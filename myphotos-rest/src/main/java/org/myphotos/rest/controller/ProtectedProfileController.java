package org.myphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.myphotos.rest.Constants.ACCESS_TOKEN_HEADER;
import static org.myphotos.rest.StatusMessages.ACCESS_FORBIDDEN;
import static org.myphotos.rest.StatusMessages.INTERRNAL_ERROR;
import static org.myphotos.rest.StatusMessages.INVALID_ACCESS_TOKEN;
import static org.myphotos.rest.StatusMessages.SERVICE_UNAVAILABLE;

import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import org.myphotos.rest.model.ErrorMessageREST;
import org.myphotos.rest.model.ImageLinkREST;
import org.myphotos.rest.model.UpdateProfileREST;
import org.myphotos.rest.model.UploadImageREST;
import org.myphotos.rest.model.UploadPhotoResultREST;
import org.myphotos.rest.model.ValidationResultREST;
import org.myphotos.rest.security.UpdateProfileService;
import org.myphotos.rest.validation.ConstraintViolationConverter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("profile")
@Path("/profile")
@Produces({APPLICATION_JSON})
@RequestScoped
@ApiResponses({
	@ApiResponse(code = 401, message = INVALID_ACCESS_TOKEN, response = ErrorMessageREST.class),
	@ApiResponse(code = 403, message = ACCESS_FORBIDDEN, response = ErrorMessageREST.class),
	@ApiResponse(code = 500, message = INTERRNAL_ERROR, response = ErrorMessageREST.class),
	@ApiResponse(code = 502, message = SERVICE_UNAVAILABLE),
	@ApiResponse(code = 503, message = SERVICE_UNAVAILABLE),
	@ApiResponse(code = 504, message = SERVICE_UNAVAILABLE)
})
public class ProtectedProfileController {

	@Inject
	private UpdateProfileService profileService;
	
	@Resource(lookup = "java:comp/Validator")
	private Validator validator;
	
	@Inject
	private ConstraintViolationConverter constraintViolationConverter;
	
	@PUT
	@Path("/{id}")
	@Consumes(APPLICATION_JSON)
	@ApiOperation(value = "Update profile by id", notes = "")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Update data has validation errors (all messages are locale depended)",
				response = ValidationResultREST.class),
		@ApiResponse(code = 404, message = "Profile with provided id not found", response = ErrorMessageREST.class)
	})
	public Response updateProfile(
			@ApiParam(value = "Profile unique numeric id", required = true)
			@PathParam("id") Long id,
			@ApiParam(value = "Access token", required = true)
			@HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
			@ApiParam(value = "Update data")
			UpdateProfileREST profileUpdate) {
		Set<ConstraintViolation<UpdateProfileREST>> violations = validator.validate(profileUpdate, Default.class);
		
		if (violations.isEmpty()) {
			profileService.updateProfile(id, accessToken, profileUpdate);
			return Response.ok().build();
		} else {
			ValidationResultREST validationResult = constraintViolationConverter.convert(violations);
			return Response.status(BAD_REQUEST).entity(validationResult).build();
		}
	}
	
	@POST
	@Path("/{id}/avatar")
	@Consumes(MULTIPART_FORM_DATA)
	@ApiOperation(value = "Upload new avatar image for profile by id", notes = "Supported formats: jpg, png",
		response = ImageLinkREST.class)
	@ApiResponses({
		@ApiResponse(code = 404, message = "Profile with provided id not found", response = ErrorMessageREST.class)
	})
	public void uploadAvatar(
			@Suspended final AsyncResponse asyncResponse,
			@ApiParam(value = "Profile unique numeric id", required = true)
			@PathParam("id") Long id,
			@ApiParam(value = "Access token", required = true)
			@HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
			@ApiParam(value = "file to upload")
			UploadImageREST imageUpload) {
		profileService.uploadAvatar(id, accessToken, imageUpload, asyncResponse);
	}
	
	@POST
	@Path("/{id}/photo")
	@Consumes(MULTIPART_FORM_DATA)
	@ApiOperation(value = "Upload new photo for profile by id", notes = "Supported formats: jpg, png",
		response = UploadPhotoResultREST.class)
	@ApiResponses({
		@ApiResponse(code = 404, message = "Profile with provided id not found", response = ErrorMessageREST.class)
	})
	public void uploadPhoto(
			@Suspended final AsyncResponse asyncResponse,
			@ApiParam(value = "Profile unique numeric id", required = true)
			@PathParam("id") Long id,
			@ApiParam(value = "Access token", required = true)
			@HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
			@ApiParam(value = "file to upload")
			UploadImageREST imageUpload) {
		profileService.uploadPhoto(id, accessToken, imageUpload, asyncResponse);
	}
}
