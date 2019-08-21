package org.myphotos.web.controller.profile;

import static org.myphotos.config.Constants.DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.web.model.MultipartImageResource;
import org.myphotos.web.router.Router;
import org.myphotos.web.security.SecurityUtils;

/**
 * @see https://docs.fineuploader.com
 * @author Vitaliy Dragun
 *
 * @param <T>
 */
abstract class AbstractUploadController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	protected Logger logger;
	
	@Inject
	protected ProfileService profileService;
	
	@Inject
	private Router router;
	
	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Part part = req.getPart("qqfile");
		Profile profile = getCurrentProfile();
		
		ImageUploadAsyncCallback asyncCallback = buildAsyncCallback(req, resp);
		
		uploadImageAsynchronously(profile, new MultipartImageResource(part), asyncCallback);
	}

	protected Profile getCurrentProfile() {
		return profileService.findById(SecurityUtils.getAuthenticatedUser().getId());
	}

	private ImageUploadAsyncCallback buildAsyncCallback(HttpServletRequest req, HttpServletResponse resp) {
		final AsyncContext asyncContext = req.startAsync(req, resp);
		asyncContext.setTimeout(DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS);
		return new ImageUploadAsyncCallback(asyncContext);
	}
	
	protected abstract void uploadImageAsynchronously(Profile profile, MultipartImageResource imageResource, AsyncOperation<T> asyncCallback);
	
	protected void handleSuccess(AsyncContext asyncContext, T result) {
		try {
			handleSuccessfulUploadResult(asyncContext, result);
		} finally {
			asyncContext.complete();
		}
	}
	
	protected void handleFailure(Throwable throwable, AsyncContext asyncContext) {
		try {
			handleFailedUploadResult(throwable, asyncContext);
		} finally {
			asyncContext.complete();
		}
	}
	
	private void handleSuccessfulUploadResult(AsyncContext asyncContext, T result) {
		JsonObject json = buildSuccessJsonResponse(asyncContext, result);
		sendJsonResponse(json, asyncContext);
	}

	private void handleFailedUploadResult(Throwable throwable, AsyncContext asyncContext) {
		logger.log(Level.SEVERE, "Sync operation failed: " + throwable.getMessage(), throwable);
		JsonObject json = buildFailureJsonResponse();
		sendJsonResponse(json, asyncContext);
	}
	
	private JsonObject buildSuccessJsonResponse(AsyncContext asyncContext, T result) {
		JsonObjectBuilder builder = Json.createObjectBuilder().add("success", true);
		for (Map.Entry<String, String> entry : getSuccessfulResponseJsonContent(result, (HttpServletRequest) asyncContext.getRequest()).entrySet()) {
			builder.add(entry.getKey(), entry.getValue());
		}
		return builder.build();
	}

	private JsonObject buildFailureJsonResponse() {
		JsonObjectBuilder builder = Json.createObjectBuilder().add("success", false);
		return builder.build();
	}
	
	protected void sendJsonResponse(JsonObject json, AsyncContext asyncContext) {
		try {
			HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
			// https://docs.fineuploader.com/endpoint_handlers/traditional.html#response
			router.sendJsonAsText(json, response);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "sendJsonResponse failed: " + e.getMessage(), e);
		}
	}

	protected abstract Map<String, String> getSuccessfulResponseJsonContent(T result, HttpServletRequest request);
	
	private class ImageUploadAsyncCallback implements AsyncOperation<T> {
		private final AsyncContext asyncContext;
		
		private ImageUploadAsyncCallback(AsyncContext asyncContext) {
			this.asyncContext = asyncContext;
		}

		@Override
		public long getTimeOutInMillis() {
			return asyncContext.getTimeout();
		}

		@Override
		public void onSuccess(T result) {
			handleSuccess(asyncContext, result);
		}

		@Override
		public void onFail(Throwable throwable) {
			handleFailure(throwable, asyncContext);
		}
		
	}
}
