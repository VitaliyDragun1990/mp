package org.myphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.myphotos.rest.Constants.ACCESS_TOKEN_HEADER;
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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.infra.util.CommonUtils;
import org.myphotos.rest.model.AuthenticationCodeREST;
import org.myphotos.rest.model.ErrorMessageREST;
import org.myphotos.rest.model.ProfileREST;
import org.myphotos.rest.model.SignUpProfileREST;
import org.myphotos.rest.model.SimpleProfileREST;
import org.myphotos.rest.model.ValidationResultREST;
import org.myphotos.rest.security.AuthenticationManager;
import org.myphotos.rest.security.AuthenticationManager.AuthenticationResult;
import org.myphotos.rest.validation.ConstraintViolationConverter;
import org.myphotos.social.exception.SocialDataRetrievingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api("auth")
@Path("/auth")
@Produces(APPLICATION_JSON)
@RequestScoped
@ApiResponses({
	@ApiResponse(code = 500, message = INTERRNAL_ERROR, response = ErrorMessageREST.class),
	@ApiResponse(code = 502, message = SERVICE_UNAVAILABLE),
	@ApiResponse(code = 503, message = SERVICE_UNAVAILABLE),
	@ApiResponse(code = 504, message = SERVICE_UNAVAILABLE)
})
public class AuthenticationContoller {

	@Resource(lookup = "java:comp/Validator")
	private Validator validator;
	
	@Inject
	private AuthenticationManager authManager;
	
	@Inject
	private ConstraintViolationConverter constraintViolationConverter;
	
	@POST
	@Path("/sign-in/facebook")
	@Consumes(APPLICATION_JSON)
	@ApiOperation(value = "Sign in via Facebook", response = SimpleProfileREST.class,
			notes = "Parameter 'authenticationCode' - code retrieved from Facebook after successful authentication"
					+ " and authorization took place")
	@ApiResponses({
		@ApiResponse(code = 401, message = "Invalid authentication code", response = ErrorMessageREST.class),
		@ApiResponse(code = 404,
		message = "Profile not found. Sign up procedure is required. Response body contains data retrieved from Facebook account",
		response = ProfileREST.class)
	})
	public Response facebookSignIn(AuthenticationCodeREST authenticationCode) {
		validateCode(authenticationCode.getCode());
		AuthenticationResult authenticationResult = authManager.signIn(authenticationCode.getCode(), Provider.FACEBOOK);
		return buildResponse(authenticationResult);
	}
	
	@POST
	@Path("/sign-up")
	@Consumes(APPLICATION_JSON)
	@ApiOperation(
			value = "Sign up with data retrieved from social service", response = SimpleProfileREST.class,
			responseHeaders = @ResponseHeader(
					name = ACCESS_TOKEN_HEADER,
					description = "Personal access token for further authentication requests",
					response = String.class),
			notes = "Parameter 'signUpProfile' is data holder with information retrieved from social provider via "
					+ "previously provided unsaccessful  signIn request"
	)
	@ApiResponses({
		@ApiResponse(code = 400, message = "Sign up profile has validation errors (all messages are locale depended)",
				response = ValidationResultREST.class)
	})
	public Response signUp(SignUpProfileREST signUpProfile) {
		return processSignUp(signUpProfile);
	}
	
	@POST
	@Path("/sign-in/google-plus")
	@ApiOperation(value = "Sign in via Google Plus", response = SimpleProfileREST.class,
		notes = "Parameter 'authenticationCode' - code retrieved from Google Plus after successful authentication"
			+ " and authorization took place")
	@ApiResponses({
		@ApiResponse(code = 401, message = "Invalid authentication code", response = ErrorMessageREST.class),
		@ApiResponse(code = 404,
			message = "Profile not found. Sign up procedure is required. Response body contains data retrieved from Google account",
			response = ProfileREST.class)
	})
	public Response goolgePlusSignIn(AuthenticationCodeREST authenticationCode) {
		validateCode(authenticationCode.getCode());
		AuthenticationResult authenticationResult = authManager.signIn(authenticationCode.getCode(), Provider.GOOGLE);
		return buildResponse(authenticationResult);
	}
	
	@POST
	@Path("/sign-out")
	@ApiOperation(value = "Invalidates access token on server",
			notes = "Response status 401 means successful sign out")
	@ApiResponses({
		@ApiResponse(code = 401, message = INVALID_ACCESS_TOKEN, response = ErrorMessageREST.class)
	})
	public Response signOut(
			@ApiParam(value = "Access token", required = true)
			@HeaderParam(ACCESS_TOKEN_HEADER) String token) {
		authManager.signOut(token);
		return Response.ok().build();
	}
	
	private Response processSignUp(SignUpProfileREST signUpProfile) {
		Set<ConstraintViolation<SignUpProfileREST>> violations = validator.validate(signUpProfile, Default.class);
		if (violations.isEmpty()) {
			AuthenticationResult authenticationResult = authManager.signUp(signUpProfile);
			return buildResponse(authenticationResult);
		} else {
			return buildBadRequestResponse(violations);
		}
	}

	private Response buildBadRequestResponse(Set<ConstraintViolation<SignUpProfileREST>> violations) {
		ValidationResultREST validationResult = constraintViolationConverter.convert(violations);
		return Response.status(BAD_REQUEST).entity(validationResult).build();
	}

	private void validateCode(String code) {
		if (CommonUtils.isBlank(code)) {
			throw new SocialDataRetrievingException("Code is required");
		}
	}
	
	private Response buildResponse(AuthenticationResult authResult) {
		Status status = authResult.isAuthenticated() ? Status.OK : Status.NOT_FOUND;
		ResponseBuilder builder = Response
				.status(status)
				.entity(authResult.getProfile());
		
		if (authResult.isAuthenticated()) {
			builder.header(ACCESS_TOKEN_HEADER, authResult.getAccessToken());
		}
		
		return builder.build();
	}
}
