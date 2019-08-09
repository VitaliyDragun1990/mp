package org.myphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.myphotos.rest.Constants.ACCESS_TOKEN_HEADER;

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

import org.myphotos.rest.model.UpdateProfileREST;
import org.myphotos.rest.model.UploadImageREST;
import org.myphotos.rest.model.ValidationResultREST;
import org.myphotos.rest.security.UpdateProfileService;
import org.myphotos.rest.validation.ConstraintViolationConverter;

@Path("/profile")
@Produces({APPLICATION_JSON})
@RequestScoped
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
	public Response updateProfile(
			@PathParam("id") Long id,
			@HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
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
	public void uploadAvatar(
			@Suspended final AsyncResponse asyncResponse,
			@PathParam("id") Long id,
			@HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
			UploadImageREST imageUpload) {
		profileService.uploadAvatar(id, accessToken, imageUpload, asyncResponse);
	}
	
	@POST
	@Path("/{id}/photo")
	@Consumes(MULTIPART_FORM_DATA)
	public void uploadPhoto(
			@Suspended final AsyncResponse asyncResponse,
			@PathParam("id") Long id,
			@HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
			UploadImageREST imageUpload) {
		profileService.uploadPhoto(id, accessToken, imageUpload, asyncResponse);
	}
}
