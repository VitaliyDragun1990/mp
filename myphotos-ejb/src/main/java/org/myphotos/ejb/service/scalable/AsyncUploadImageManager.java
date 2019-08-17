package org.myphotos.ejb.service.scalable;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.MANDATORY;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static org.myphotos.config.JMSEnvironmentSettings.JMS_CONNECTION_FACTORY_JNDI_NAME;
import static org.myphotos.config.JMSEnvironmentSettings.UPLOAD_REQUEST_QUEUE_JNDI_NAME;
import static org.myphotos.config.JMSImageResourceType.IMAGE_RESOURCE_PHOTO;
import static org.myphotos.config.JMSImageResourceType.IMAGE_RESOURCE_PROFILE_AVATAR;
import static org.myphotos.config.JMSMessageProperty.IMAGE_RESOURCE_TEMP_PATH;
import static org.myphotos.config.JMSMessageProperty.IMAGE_RESOURCE_TYPE;
import static org.myphotos.config.JMSMessageProperty.PROFILE_ID;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jms.DeliveryMode;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import org.myphotos.config.JMSImageResourceType;
import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.model.ImageResource;

/**
 * Responsible for creating and sending messages to JMS queue regarding
 * asynchronous image uploading.
 * 
 * @author Vitaliy Dragun
 *
 */
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionAttribute(MANDATORY)
public class AsyncUploadImageManager {

	@Inject
	private Logger logger;

	@Inject
	@JMSConnectionFactory(JMS_CONNECTION_FACTORY_JNDI_NAME)
	private JMSContext context;

	@Resource(lookup = UPLOAD_REQUEST_QUEUE_JNDI_NAME)
	private Queue uploadRequestQueue;

	private final Map<String, AsyncOperationItem> asyncOperationMap = new ConcurrentHashMap<>();

	public void uploadNewAvatar(Profile profile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation) {
		upload(profile, imageResource, asyncOperation, IMAGE_RESOURCE_PROFILE_AVATAR);
	}

	public void uploadNewPhoto(Profile profile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation) {
		upload(profile, imageResource, asyncOperation, IMAGE_RESOURCE_PHOTO);
	}

	public void completeUploadNewAvatarSuccess(String path, Profile profile) {
		completeAsyncOperationSuccess(IMAGE_RESOURCE_PROFILE_AVATAR, path, profile);
	}

	public void completeUploadNewPhotoSuccess(String path, Photo photo) {
		completeAsyncOperationSuccess(IMAGE_RESOURCE_PHOTO, path, photo);
	}

	public void completeUploadNewAvatarFail(String path, Throwable throwable) {
		completeAsyncOperationFail(IMAGE_RESOURCE_PROFILE_AVATAR, path, throwable);
	}

	public void completeUploadNewPhotoFail(String path, Throwable throwable) {
		completeAsyncOperationFail(IMAGE_RESOURCE_PHOTO, path, throwable);
	}

	private void upload(Profile profile, ImageResource imageResource, AsyncOperation<?> asyncOperation,
			JMSImageResourceType imageResourceType) {
		Path uploadPath = imageResource.getPath();
		String imageResourcePath = uploadPath.toString();
		
		asyncOperationMap.put(imageResourcePath, new AsyncOperationItem(asyncOperation));
		logger.log(Level.INFO, "Save asyncOperation for path: {0}[{1}]",
				new Object[] { imageResourceType, imageResourcePath });

		sendUploadRequest(imageResourcePath, profile, imageResourceType, asyncOperation.getTimeOutInMillis());
	}

	private void sendUploadRequest(String imageResourcePath, Profile profile, JMSImageResourceType imageResourceType,
			long ttl) {
		Map<String, Object> message = buildUploadRequestMessage(imageResourcePath, profile, imageResourceType);

		context
			.createProducer()
			.setTimeToLive(ttl)
			.setDeliveryMode(DeliveryMode.NON_PERSISTENT)
			.send(uploadRequestQueue, message);

	}

	private Map<String, Object> buildUploadRequestMessage(String imageResourcePath, Profile profile,
			JMSImageResourceType imageResourceType) {
		Map<String, Object> message = new HashMap<>();

		message.put(IMAGE_RESOURCE_TEMP_PATH.name(), imageResourcePath);
		message.put(IMAGE_RESOURCE_TYPE.name(), imageResourceType.name());
		message.put(PROFILE_ID.name(), profile.getId());

		return message;
	}

	@SuppressWarnings("unchecked")
	private <T> void completeAsyncOperationSuccess(JMSImageResourceType imageResourceType, String imageResourcePath,
			T entity) {
		AsyncOperationItem asyncOperationItem = asyncOperationMap.remove(imageResourcePath);
		if (asyncOperationItem != null) {
			logger.log(Level.INFO, "Async operation for path: {0} [{1}] completed successfully",
					new Object[] { imageResourceType, imageResourcePath });
			((AsyncOperation<T>) asyncOperationItem.getAsyncOperation()).onSuccess(entity);
		} else {
			logger.log(Level.SEVERE, "Async operation for path: {0}[{1}] not found",
					new Object[] { imageResourceType, imageResourcePath });
		}
	}

	private void completeAsyncOperationFail(JMSImageResourceType imageResourceType, String imageResourcePath,
			Throwable throwable) {
		AsyncOperationItem asyncOperationItem = asyncOperationMap.remove(imageResourcePath);
		if (asyncOperationItem != null) {
			logger.log(Level.SEVERE, "Async operation for path: {0} [{1}] failed",
					new Object[] { imageResourceType, imageResourcePath });
			asyncOperationItem.getAsyncOperation().onFail(throwable);
		} else {
			logger.log(Level.SEVERE, "Async operation for path: {0}[{1}] not found",
					new Object[] { imageResourceType, imageResourcePath });
		}
	}

	@Schedule(hour = "0", minute = "0", second = "0", persistent = false)
	@TransactionAttribute(NOT_SUPPORTED)
	void removeTimedOutAsyncOperations() {
		for (Map.Entry<String, AsyncOperationItem> entry : asyncOperationMap.entrySet()) {
			if (entry.getValue().isExpired()) {
				asyncOperationMap.remove(entry.getKey());
				logger.log(Level.WARNING, "AsyncOperation for path: {0} removed by timeout ", entry.getKey());
			}
		}
	}

	private static class AsyncOperationItem {
		private final AsyncOperation<?> asyncOperation;
		private final long created;

		public AsyncOperationItem(AsyncOperation<?> asyncOperation) {
			this.asyncOperation = asyncOperation;
			this.created = System.currentTimeMillis();
		}

		public AsyncOperation<?> getAsyncOperation() {
			return asyncOperation;
		}

		public boolean isExpired() {
			return created + asyncOperation.getTimeOutInMillis() < System.currentTimeMillis();
		}
	}
}
