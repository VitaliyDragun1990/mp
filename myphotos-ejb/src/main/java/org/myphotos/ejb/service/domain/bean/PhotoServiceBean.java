package org.myphotos.ejb.service.domain.bean;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.model.SortMode;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.infra.exception.business.ObjectNotFoundException;
import org.myphotos.infra.exception.business.ValidationException;
import org.myphotos.infra.repository.PhotoRepository;
import org.myphotos.infra.repository.ProfileRepository;

@Stateless
@LocalBean
@Local(PhotoService.class)
public class PhotoServiceBean implements PhotoService {
	
	private PhotoRepository photoRepository;
	private ProfileRepository profileRepository;
	private SessionContext sessionContext;
	
	@Inject
	void setPhotoRepository(PhotoRepository photoRepository) {
		this.photoRepository = photoRepository;
	}
	
	@Inject
	void setProfileRepository(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
	@Resource
	void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	@Override
	public List<Photo> findProfilePhotos(Long profileId, Pageable pageable) {
		return photoRepository.findProfilePhotoLatestFirst(profileId, pageable.getOffset(), pageable.getLimit());
	}

	@Override
	public List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable) {
		switch (sortMode) {
		case PHOTO_POPULARITY:
			return photoRepository.findAllOrderedByProfileRatingDesc(pageable.getOffset(), pageable.getLimit());
		case AUTHOR_POPULARITY:
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
		
		throw new UnsupportedOperationException("implement me");
	}

	@Override
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void uploadNewPhoto(Profile profile, ImageResource imageResource, AsyncOperation<Photo> callback) {
		try {
			Photo photo = null; // FIXME
			callback.onSuccess(photo);
		} catch (Throwable th) {
			sessionContext.setRollbackOnly();
			callback.onFail(th);
		}
	}

}
