package org.myphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.infra.util.CommonUtils;
import org.myphotos.rest.model.AuthenticationCodeREST;
import org.myphotos.rest.model.SignUpProfileREST;
import org.myphotos.rest.model.ValidationResultREST;
import org.myphotos.rest.security.AuthenticationManager;
import org.myphotos.rest.security.AuthenticationManager.AuthenticationResult;
import org.myphotos.rest.validation.ConstraintViolationConverter;
import org.myphotos.social.exception.SocialDataRetrievingException;

@Path("/auth")
@Produces(APPLICATION_JSON)
@RequestScoped
public class AuthenticationContoller {

	@Resource(lookup = "java:comp/Validator")
	private Validator validator;
	
	@Inject
	private AuthenticationManager securityManager;
	
	@Inject
	private ConstraintViolationConverter constraintViolationConverter;
	
	@POST
	@Path("/sign-in/facebook")
	@Consumes(APPLICATION_JSON)
	public Response facebookSignIn(AuthenticationCodeREST authenticationCode) {
		validateCode(authenticationCode.getCode());
		AuthenticationResult authenticationResult = securityManager.signIn(authenticationCode.getCode(), Provider.FACEBOOK);
		return buildResponse(authenticationResult);
	}
	
	@POST
	@Path("/sign-up/facebook")
	@Consumes(APPLICATION_JSON)
	public Response facebookSignUp(SignUpProfileREST signUpProfile) {
		return processSignUp(signUpProfile);
	}
	
	@POST
	@Path("/sign-in/google-plus")
	public Response goolgePlusSignIn(AuthenticationCodeREST authenticationCode) {
		validateCode(authenticationCode.getCode());
		AuthenticationResult authenticationResult = securityManager.signIn(authenticationCode.getCode(), Provider.GOOGLE);
		return buildResponse(authenticationResult);
	}
	
	@POST
	@Path("/sign-up/google-plus")
	@Consumes(APPLICATION_JSON)
	public Response googlePlusSignUp(SignUpProfileREST signUpProfile) {
		return processSignUp(signUpProfile);
	}
	
	@POST
	@Path("/sign-out")
	public Response signOut(
			@HeaderParam(ACCESS_TOKEN_HEADER) String token) {
		securityManager.signOut(token);
		return Response.ok().build();
	}
	
	private Response processSignUp(SignUpProfileREST signUpProfile) {
		Set<ConstraintViolation<SignUpProfileREST>> violations = validator.validate(signUpProfile, Default.class);
		if (violations.isEmpty()) {
			AuthenticationResult authenticationResult = securityManager.signUp(signUpProfile);
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
