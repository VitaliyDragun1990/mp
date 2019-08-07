package org.myphotos.ws.service.bean;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.myphotos.converter.ModelConverter;
import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.ws.error.ExceptionMapperInterceptor;
import org.myphotos.ws.model.ProfilePhotoSOAP;
import org.myphotos.ws.model.ProfilePhotosSOAP;
import org.myphotos.ws.model.ProfileSOAP;
import org.myphotos.ws.service.ProfileWebService;

@Singleton
@ConcurrencyManagement(BEAN)
@WebService(
		portName = "ProfileServicePort",
		serviceName = "ProfileService",
		targetNamespace = "http://soap.myphotos.com/ws/ProfileService?wsdl",
		endpointInterface = "org.myphotos.ws.service.ProfileWebService"
		)
@Interceptors(ExceptionMapperInterceptor.class)
public class ProfileWebServiceBean implements ProfileWebService {
	
	@EJB
	private ProfileService profileService;
	
	@EJB
	private PhotoService photoService;
	
	@Inject
	private ModelConverter modelConverter;

	@Override
	public ProfileSOAP findById(Long id, boolean withPhotos, int limit) {
		Profile profile = profileService.findById(id);
		ProfileSOAP result = modelConverter.convert(profile, ProfileSOAP.class);
		if (withPhotos) {
			result.setPhotos(findPhotos(profile.getId(), 1, limit));
		}
		return result;
	}

	@Override
	public ProfilePhotosSOAP findProfilePhotos(Long profileId, int page, int limit) {
		ProfilePhotosSOAP photos = new ProfilePhotosSOAP();
		photos.setPhotos(findPhotos(limit, page, limit));
		return photos;
	}
	
	private List<ProfilePhotoSOAP> findPhotos(long profileId, int page, int limit) {
		List<Photo> photos = photoService.findProfilePhotos(profileId, Pageable.of(page, limit));
		return modelConverter.convertList(photos, ProfilePhotoSOAP.class);
	}

	void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	void setModelConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}
	
}
