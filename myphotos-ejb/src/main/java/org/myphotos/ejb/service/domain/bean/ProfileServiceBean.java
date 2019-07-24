package org.myphotos.ejb.service.domain.bean;

import java.util.Optional;

import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.infra.cdi.annotation.Property;
import org.myphotos.infra.exception.business.ObjectNotFoundException;
import org.myphotos.infra.repository.ProfileRepository;

@Stateless
@LocalBean
@Local(ProfileService.class)
public class ProfileServiceBean implements ProfileService {
	
	private String avatarPlaceholderUrl;
	private ProfileRepository profileRepository;
	
	@Inject
	public void setAvatarPlaceholderUrl(
			@Property("myphotos.profile.avatar.placeholder.url") String avatarPlaceholderUrl) {
		this.avatarPlaceholderUrl = avatarPlaceholderUrl;
	}
	
	@Inject
	public void setProfileRepository(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

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
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Profile profile) {
		profileRepository.update(profile);
	}

	@Override
	@Asynchronous
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
		// TODO: add implementation
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

}
