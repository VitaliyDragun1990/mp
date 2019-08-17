package org.myphotos.ejb.service.scalable;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static org.myphotos.config.JMSEnvironmentSettings.UPLOAD_RESPONSE_QUEUE_JNDI_NAME;
import static org.myphotos.config.JMSImageResourceType.IMAGE_RESOURCE_PHOTO;
import static org.myphotos.config.JMSMessageProperty.IMAGE_RESOURCE_TEMP_PATH;
import static org.myphotos.config.JMSMessageProperty.IMAGE_RESOURCE_TYPE;
import static org.myphotos.config.JMSMessageProperty.PROFILE_ID;
import static org.myphotos.config.JMSMessageProperty.REQUEST_SUCCESS;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.myphotos.config.JMSImageResourceType;
import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.ejb.service.domain.PhotoServiceBean;
import org.myphotos.ejb.service.domain.ProfileServiceBean;

/**
 * Responsible for processing upload responses from JMS queue.
 * 
 * @author Vitaliy Dragun
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = UPLOAD_RESPONSE_QUEUE_JNDI_NAME) })
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class UploadResponseProcessorMDBean implements MessageListener {

	@Inject
	private Logger logger;

	@EJB
	private PhotoServiceBean photoServiceBean;

	@EJB
	private ProfileServiceBean profileServiceBean;

	@EJB
	private AsyncUploadImageManager asyncUploadImageManager;

	@Resource
	private MessageDrivenContext messageDrivenContext;

	@Override
	public void onMessage(Message jmsMessage) {
		try {
			processMessage(jmsMessage);
		} catch (JMSException e) {
			messageDrivenContext.setRollbackOnly();
			logger.log(Level.SEVERE,
					UploadResponseProcessorMDBean.class.getName() + ".onMessage failed: " + e.getMessage(), e);
		}
	}

	private void processMessage(Message jmsMessage) throws JMSException {
		if (isRedelivered(jmsMessage)) {
			logger.log(Level.WARNING, "Ignoring redelivered message for path {0}",
					jmsMessage.getStringProperty(IMAGE_RESOURCE_TEMP_PATH.name()));
		} else {
			processNewMessage((ObjectMessage) jmsMessage);
		}
	}

	private boolean isRedelivered(Message jmsMessage) throws JMSException {
		return jmsMessage.getJMSRedelivered();
	}

	private void processNewMessage(ObjectMessage message) throws JMSException {
		String imageResourcePath = message.getStringProperty(IMAGE_RESOURCE_TEMP_PATH.name());
		JMSImageResourceType imageResourceType = JMSImageResourceType.valueOf(
				message.getStringProperty(IMAGE_RESOURCE_TYPE.name()));
		
		processNewMessage(message, imageResourcePath, imageResourceType);
	}

	private void processNewMessage(ObjectMessage message, String imageResourcePath,
			JMSImageResourceType imageResourceType) throws JMSException {
		Long profileId = message.getLongProperty(PROFILE_ID.name());
		
		if (message.getBooleanProperty(REQUEST_SUCCESS.name())) {
			processSuccessMessage(profileId, message, imageResourceType, imageResourcePath);
		} else {
			processFailMessage(profileId, message, imageResourceType, imageResourcePath);
		}
	}

	private void processSuccessMessage(Long profileId, ObjectMessage message, JMSImageResourceType imageResourceType,
			String imageResourcePath) throws JMSException {
		if (imageResourceType == IMAGE_RESOURCE_PHOTO) {
			processSuccessPhotoMessage(profileId, message, imageResourcePath);
		} else {
			processSuccessAvatarMessage(profileId, message, imageResourcePath);
		}
	}
	
	private void processSuccessPhotoMessage(Long profileId, ObjectMessage message, String imageResourcePath)
			throws JMSException {
		Photo photo = (Photo) message.getObject();
		photoServiceBean.createNewPhoto(profileId, photo);
		asyncUploadImageManager.completeUploadNewPhotoSuccess(imageResourcePath, photo);
	}

	private void processSuccessAvatarMessage(Long profileId, ObjectMessage message, String imageResourcePath)
			throws JMSException {
		Profile profile = (Profile) message.getObject();
		profileServiceBean.uploadNewAvatar(profileId, profile.getAvatarUrl());
		asyncUploadImageManager.completeUploadNewAvatarSuccess(imageResourcePath, profile);
	}

	private void processFailMessage(Long profileId, ObjectMessage message, JMSImageResourceType imageResourceType,
			String imageResourcePath) throws JMSException {
		Throwable throwable = (Throwable) message.getObject();
		
		if (imageResourceType == IMAGE_RESOURCE_PHOTO) {
			processFailPhotoMessage(imageResourcePath, throwable);
		} else {
			processFailAvatarMessage(profileId, imageResourcePath, throwable);
		}
	}
	
	private void processFailPhotoMessage(String imageResourcePath, Throwable throwable) {
		asyncUploadImageManager.completeUploadNewPhotoFail(imageResourcePath, throwable);
	}

	private void processFailAvatarMessage(Long profileId, String imageResourcePath, Throwable throwable) {
		profileServiceBean.setAvatarPlaceholder(profileId);
		asyncUploadImageManager.completeUploadNewAvatarFail(imageResourcePath, throwable);
	}
}
