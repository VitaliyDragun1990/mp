package org.myphotos.ejb.service.domain;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.model.SortMode;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.ejb.repository.jpa.DBSource;
import org.myphotos.ejb.service.scalable.AsyncUploadImageManager;
import org.myphotos.infra.exception.business.ObjectNotFoundException;
import org.myphotos.infra.exception.business.ValidationException;
import org.myphotos.infra.repository.PhotoRepository;
import org.myphotos.infra.repository.ProfileRepository;
import org.myphotos.media.ImageService;

@Stateless
@LocalBean
@Local(PhotoService.class)
public class PhotoServiceBean implements PhotoService {
	
	private PhotoRepository photoRepository;
	private ProfileRepository profileRepository;
	private ImageService imageService;
	private SessionContext sessionContext;
	private AsyncUploadImageManager asyncUploadImageManager;

	@Override
	public List<Photo> findProfilePhotos(Long profileId, Pageable pageable) {
		return photoRepository.findProfilePhotosLatestFirst(profileId, pageable.getOffset(), pageable.getLimit());
	}

	@Override
	public List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable) {
		switch (sortMode) {
		case POPULAR_AUTHOR:
			return photoRepository.findAllOrderedByProfileRatingDesc(pageable.getOffset(), pageable.getLimit());
		case POPULAR_PHOTO:
			return photoRepository.findAllOrderedByViewsDesc(pageable.getOffset(), pageable.getLimit());
		default:
			throw new ValidationException("Unsupported sort mode: " + sortMode);
		}
	}

	@Override
	public long countAllPhotos() {
		return photoRepository.countAll();
	}

	@Override
	public String viewLargePhoto(Long photoId) throws ObjectNotFoundException {
		Photo photo = getPhoto(photoId);
		photo.setViews(photo.getViews() + 1);
		photoRepository.update(photo);
		return photo.getLargeUrl();
	}
	
	public Photo getPhoto(Long photoId) throws ObjectNotFoundException {
		Optional<Photo> photo = photoRepository.findById(photoId);
		if (!photo.isPresent()) {
			throw new ObjectNotFoundException(String.format("Photo with id: %s not found", photoId));
		}
		return photo.get();
	}

	@Override
	public Image downloadOriginalPhoto(Long photoId) throws ObjectNotFoundException {
		Photo photo = getPhoto(photoId);
		photo.setDownloads(photo.getDownloads() + 1);
		photoRepository.update(photo);
		
		return imageService.downloadImage(photo.getOriginalUrl());
	}

	/*@Override
	@Asynchronous
	@Interceptors(AsyncOperationInterceptor.class)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void uploadNewPhoto(Profile profile, ImageResource imageResource, AsyncOperation<Photo> callback) {
		try {
			Photo photo = uploadNewPhoto(profile, imageResource);
			callback.onSuccess(photo);
		} catch (Throwable th) {
			sessionContext.setRollbackOnly();
			callback.onFail(th);
		}
	}*/
	
	@Override
	public void uploadNewPhoto(Profile profile, ImageResource imageResource, AsyncOperation<Photo> callback) {
		asyncUploadImageManager.uploadNewPhoto(profile, imageResource, callback);
	}

	public Photo uploadNewPhoto(Profile profile, ImageResource imageResource) {
		Photo photo = imageService.uploadPhoto(imageResource);
		
		createNewProfilePhoto(profile, photo);
		
		return photo;
	}
	
	public void createNewPhoto(Long profileId, Photo photo) {
		createNewProfilePhoto(profileRepository.findById(profileId).get(), photo);
	}

	private void createNewProfilePhoto(Profile profile, Photo photo) {
		photo.setProfile(profile);
		photoRepository.create(photo);
		photoRepository.flush();
		
		profile.setPhotoCount(photoRepository.countProfilePhotos(profile.getId()));
		profileRepository.update(profile);
	}

	@Inject
	void setPhotoRepository(@DBSource PhotoRepository photoRepository) {
		this.photoRepository = photoRepository;
	}
	
	@Inject
	void setProfileRepository(@DBSource ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
	@Inject
	void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@Resource
	void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
	
	@EJB
	void setAsyncUploadImageManager(AsyncUploadImageManager asyncUploadImageManager) {
		this.asyncUploadImageManager = asyncUploadImageManager;
	}
}
