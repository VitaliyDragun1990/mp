package org.myphotos.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.myphotos.cdi.interceptor.AsyncOperationInterceptor;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.generator.ProfileUidManager;
import org.myphotos.infra.cdi.qualifier.Property;
import org.myphotos.infra.exception.business.ObjectNotFoundException;
import org.myphotos.infra.repository.ProfileRepository;
import org.myphotos.infra.util.CommonUtils;
import org.myphotos.media.ImageService;
import org.myphotos.media.model.ImageCategory;
import org.myphotos.media.model.URLImageResource;
import org.myphotos.repository.jpa.DBSource;

@Stateless
@LocalBean
@Local(ProfileService.class)
public class ProfileServiceBean implements ProfileService {
	
	private String avatarPlaceholderUrl;
	private ProfileRepository profileRepository;
	private ImageService imageService;
	private ProfileUidManager uidManager;

	@Override
	public Profile findById(Long id) throws ObjectNotFoundException {
		Optional<Profile> profile = profileRepository.findById(id);
		if (!profile.isPresent()) {
			throw new ObjectNotFoundException(String.format("Profile with id: %s not found", id));
		}
		return profile.get();
	}

	@Override
	public Profile findByUid(String uid) throws ObjectNotFoundException {
		Optional<Profile> profile = profileRepository.findByUid(uid);
		if (!profile.isPresent()) {
			throw new ObjectNotFoundException(String.format("Profile with uid: '%s' not found", uid));
		}
		return profile.get();
	}

	@Override
	public Optional<Profile> findByEmail(String email) {
		return profileRepository.findByEmail(email);
	}

	@Override
	public void create(Profile profile, boolean uploadProfileAvatar) {
		setProfileUidIfAbsent(profile);
		profileRepository.create(profile);
		if (uploadProfileAvatar && CommonUtils.isNotBlank(profile.getAvatarUrl())) {
			uploadNewAvatar(profile, new URLImageResource(profile.getAvatarUrl()));
		}
	}

	@Override
	public void update(Profile profile) {
		profileRepository.update(profile);
	}

	@Override
	@Asynchronous
	@Interceptors(AsyncOperationInterceptor.class)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void uploadNewAvatar(Profile profile, ImageResource imageResource,
			AsyncOperation<Profile> callback) {
		try {
			uploadNewAvatar(profile, imageResource);
			callback.onSuccess(profile);
		} catch (Throwable th) {
			setAvatarPlaceholder(profile);
			callback.onFail(th);
		}
	}

	public void uploadNewAvatar(Profile profile, ImageResource imageResource) {
		String avatarUrl = imageService.uploadAvatar(imageResource);
		if (isProfileAvatarNotPlaceholder(profile.getAvatarUrl())) {
			imageService.removeImage(profile.getAvatarUrl());
		}
		profile.setAvatarUrl(avatarUrl);
		profileRepository.update(profile);
	}

	private boolean isProfileAvatarNotPlaceholder(String avatarUrl) {
		return ImageCategory.isImageCategoryUrl(avatarUrl);
	}
	
	public void setAvatarPlaceholder(Long profileId) {
		Optional<Profile> profile = profileRepository.findById(profileId);
		if (profile.isPresent()) {
			setAvatarPlaceholder(profile.get());
		} else {
			throw new ObjectNotFoundException(
					String.format("Can not set avatar for profile with id: %s - profile not found", profileId));
		}
	}
	
	public void setAvatarPlaceholder(Profile profile) {
		if (profile.getAvatarUrl() == null) {
			profile.setAvatarUrl(avatarPlaceholderUrl);
			profileRepository.update(profile);
		}
	}
	
	private void setProfileUidIfAbsent(Profile profile) {
		if (profile.getUid() == null) {
			profile.setUid(generateUid(profile.getFirstName(), profile.getLastName()));
		}
	}
	
	private String generateUid(String firstName, String lastName) {
		return generateCustomUid(firstName, lastName).orElse(uidManager.getDefaultUid());
	}
	
	private Optional<String> generateCustomUid(String firstName, String lastName) {
		List<String> uids = uidManager.getProfileUidCandidates(firstName, lastName);
		List<String> occupiedUids = profileRepository.findUids(uids);
		uids = uids.stream().filter(uid -> !occupiedUids.contains(uid)).collect(Collectors.toList());
		
		return uids.isEmpty() ? Optional.empty() : Optional.of(uids.get(0));
	}

	@Inject
	void setAvatarPlaceholderUrl(
			@Property("myphotos.profile.avatar.placeholder.url") String avatarPlaceholderUrl) {
		this.avatarPlaceholderUrl = avatarPlaceholderUrl;
	}
	
	@Inject
	void setProfileRepository(@DBSource ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
	@Inject
	void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@Inject
	void setUidManager(ProfileUidManager uidManager) {
		this.uidManager = uidManager;
	}

}
