package org.myphotos.web.controller.profile;

import static org.myphotos.config.Constants.MAX_UPLOADED_PHOTO_SIZE_IN_BYTES;

import java.util.Collections;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.web.model.MultipartImageResource;

@WebServlet(urlPatterns = "/upload-avatar", asyncSupported = true, loadOnStartup = 1)
@MultipartConfig(maxFileSize = MAX_UPLOADED_PHOTO_SIZE_IN_BYTES)
public class UploadProfileAvatarController extends AbstractUploadController<Profile> {
	private static final long serialVersionUID = 1L;

	@Override
	protected void uploadImageAsynchronously(Profile profile, MultipartImageResource imageResource,
			AsyncOperation<Profile> asyncCallback) {
		profileService.uploadNewAvatar(profile, imageResource, asyncCallback);
	}

	@Override
	protected Map<String, String> getSuccessfulResponseJsonContent(Profile result, HttpServletRequest request) {
		return Collections.singletonMap("thumbnailUrl", result.getAvatarUrl());
	}

}
