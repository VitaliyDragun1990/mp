package org.myphotos.rest.security;

import static org.myphotos.config.Constants.DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;

import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.AsyncResponse;

import org.myphotos.converter.AbsoluteUrlConverter;
import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.rest.model.ImageLinkREST;
import org.myphotos.rest.model.UpdateProfileREST;
import org.myphotos.rest.model.UploadImageREST;
import org.myphotos.rest.model.UploadPhotoResultREST;
import org.myphotos.security.AccessTokenService;

@ApplicationScoped
class RESTUpdateProfileService implements UpdateProfileService {

	@EJB
	private AccessTokenService accessTokenService;

	@EJB
	private ProfileService profileService;

	@EJB
	private PhotoService photoService;

	@Inject
	private AbsoluteUrlConverter urlConverter;

	@Override
	public void updateProfile(Long profileId, String accessToken, UpdateProfileREST profileUpdate) {
		Profile profileToUpdate = accessTokenService.findProfile(accessToken, profileId);
		profileUpdate.copyToProfile(profileToUpdate);
		profileService.update(profileToUpdate);
	}

	@Override
	public void uploadAvatar(Long profileId, String accessToken, UploadImageREST uploadImage,
			AsyncResponse asyncResponse) {
		Profile profileToUpdate = accessTokenService.findProfile(accessToken, profileId);
		
		profileService.uploadNewAvatar(
				profileToUpdate,
				uploadImage.getImageResource(),
				new AvatarUploadAsyncOperation(asyncResponse));
	}

	@Override
	public void uploadPhoto(Long profileId, String accessToken, UploadImageREST uploadImage,
			AsyncResponse asyncResponse) {
		Profile profileToUpdate = accessTokenService.findProfile(accessToken, profileId);
		
		photoService.uploadNewPhoto(
				profileToUpdate,
				uploadImage.getImageResource(),
				new PhotoUploadAsyncOperation(asyncResponse));
	}

	void setAccessTokenService(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

	void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	void setUrlConverter(AbsoluteUrlConverter urlConverter) {
		this.urlConverter = urlConverter;
	}

	private abstract class UploadAsyncOperation<T> implements AsyncOperation<T> {
		protected final AsyncResponse response;

		public UploadAsyncOperation(AsyncResponse response) {
			this.response = response;
			this.response.setTimeout(DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS);
		}

		@Override
		public long getTimeOutInMillis() {
			return DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;
		}

		@Override
		public void onFail(Throwable throwable) {
			response.resume(throwable);
		}
	}
	private class AvatarUploadAsyncOperation extends UploadAsyncOperation<Profile> {
		
		public AvatarUploadAsyncOperation(AsyncResponse response) {
			super(response);
		}
		
		@Override
		public void onSuccess(Profile result) {
			String absoluteUrl = urlConverter.convert(result.getAvatarUrl());
			response.resume(new ImageLinkREST(absoluteUrl));
		}
	}

	private class PhotoUploadAsyncOperation extends UploadAsyncOperation<Photo> {
		
		public PhotoUploadAsyncOperation(AsyncResponse response) {
			super(response);
		}

		@Override
		public void onSuccess(Photo result) {
			String absoluteUrl = urlConverter.convert(result.getSmallUrl());
			response.resume(new UploadPhotoResultREST(result.getId(), absoluteUrl));
		}

	}

}
