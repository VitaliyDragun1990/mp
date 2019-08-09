package org.myphotos.rest.security;

import javax.ws.rs.container.AsyncResponse;

import org.myphotos.rest.model.UpdateProfileREST;
import org.myphotos.rest.model.UploadImageREST;

public interface UpdateProfileService {

	void updateProfile(Long profileId, String accessToken, UpdateProfileREST profileUpdate);
	
	void uploadAvatar(Long profileId, String accessToken, UploadImageREST uploadImage, AsyncResponse asyncResponse);
	
	void uploadPhoto(Long profileId, String accessToken, UploadImageREST uploadImage, AsyncResponse asyncResponse);
}
