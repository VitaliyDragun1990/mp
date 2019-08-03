package org.myphotos.web.controller.signup;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.security.SecurityManager;
import org.myphotos.web.router.Router;
import org.myphotos.web.security.SecurityUtils;

abstract class AbstractAuthenticationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	protected SecurityManager securityManager;
	
	@Inject
	protected Router router;
	
	protected abstract Provider getProvider();
	
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SecurityUtils.isAuthenticated()) {
			router.redirectToAuthUrl(
					"/sign-up",
					() -> "/" + SecurityUtils.getAuthenticatedProfile().getUid(),
					resp);
		} else {
			processAuthRequest(req, resp);
		}
	}

	private void processAuthRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Optional<String> code = Optional.ofNullable(req.getParameter("code"));
		if (code.isPresent()) {
			processAuthentication(code.get(), req, resp);
		} else {
			router.redirectToUrl("/", resp);	
		}
	}

	private void processAuthentication(String code, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Optional<Profile> singInProfile = securityManager.signIn(code, getProvider());
		
		if (singInProfile.isPresent()) {
			completeSignInForExistingProfile(singInProfile.get(), resp);
		} else {
			startSignUpProcessForNewProfile(code, req, resp);
		}
	}
	
	private void completeSignInForExistingProfile(Profile profile, HttpServletResponse resp) throws IOException {
		SecurityUtils.authenticate(profile);
		router.redirectToUrl("/" + profile.getUid(), resp);
	}
	
	private void startSignUpProcessForNewProfile(String code, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Profile profile = securityManager.startSignUp(code, getProvider());
		SecurityUtils.authenticateTemporary();
		req.getSession().setAttribute("signUpProfile", profile);
		router.redirectToUrl("/sign-up", resp);
	}

}
