package org.myphotos.web.controller.profile;

import static org.myphotos.config.Constants.MAX_UPLOADED_PHOTO_SIZE_IN_BYTES;
import static org.myphotos.web.Constants.PHOTO_LIMIT;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.web.model.MultipartImageResource;
import org.myphotos.web.router.Router;
import org.myphotos.web.security.SecurityUtils;

@WebServlet(urlPatterns = "/upload-photos", asyncSupported = true, loadOnStartup = 1)
@MultipartConfig(maxFileSize = MAX_UPLOADED_PHOTO_SIZE_IN_BYTES)
public class UploadProfilePhotoController extends AbstractUploadController<Photo> {
	private static final long serialVersionUID = 1L;

	@Inject
	private PhotoService photoService;
	@Inject
	private Router router;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Profile profile = SecurityUtils.getAuthenticatedProfile();
		List<Photo> photos = photoService.findProfilePhotos(
				profile.getId(),
				Pageable.of(1, PHOTO_LIMIT - 1) // PHOTO_LIMIT - 1 because we need room on page to place 'upload a file' template
				);
		
		req.setAttribute("profile", profile);
		req.setAttribute("photos", photos);
		req.setAttribute("profilePhotos", Boolean.TRUE);
		req.setAttribute("photoLimit", PHOTO_LIMIT);
		
		router.forwardToPage("upload-photos", req, resp);
	}
	
	@Override
	protected void uploadImageAsynchronously(Profile profile, MultipartImageResource imageResource,
			AsyncOperation<Photo> asyncCallback) {
		photoService.uploadNewPhoto(profile, imageResource, asyncCallback);
	}

	@Override
	protected Map<String, String> getSuccessfulResponseJsonContent(Photo photo, HttpServletRequest request) {
		Map<String, String> jsonContent = new HashMap<>();
		jsonContent.put("smallUrl", photo.getSmallUrl());
		jsonContent.put("largeUrl", photo.getLargeUrl());
		jsonContent.put("originalUrl", photo.getOriginalUrl());
		jsonContent.put("photoId", String.valueOf(photo.getId()));
		jsonContent.put("views", String.valueOf(photo.getViews()));
		jsonContent.put("downloads", String.valueOf(photo.getDownloads()));
		jsonContent.put("created", toShortDateFormat(photo.getCreated(), request.getLocale()));
		
		return jsonContent;
	}

	private String toShortDateFormat(LocalDateTime date, Locale locale) {
		return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale));
	}

}
