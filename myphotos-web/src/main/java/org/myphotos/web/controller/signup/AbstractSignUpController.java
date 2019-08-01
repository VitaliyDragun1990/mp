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
import org.myphotos.security.SignUpProcessManager;
import org.myphotos.web.router.Router;

abstract class AbstractSignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	protected SignUpProcessManager signUpProcessManager;
	
	@Inject
	protected Router router;
	
	protected abstract Provider getProvider();
	
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Optional<String> code = Optional.ofNullable(req.getParameter("code"));
		if (code.isPresent()) {
			processSignUp(code.get(), resp);
		} else {
			router.redirectToUrl("/", resp);	
		}
	}

	private void processSignUp(String code, HttpServletResponse resp) throws IOException {
		Optional<Profile> profileOptional = signUpProcessManager.tryToSignUp(code, getProvider());
		if (profileOptional.isPresent()) {
			Profile profile = profileOptional.get();
			// TODO: Authenticate ???
			router.redirectToUrl("/" + profile.getUid(), resp);
		} else {
			// TODO Authenticate ???
			router.redirectToUrl("/sign-up", resp);
		}
	}

}
