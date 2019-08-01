package org.myphotos.web.controller.signup;

import javax.servlet.annotation.WebServlet;

import org.myphotos.infra.cdi.annotation.SocialProvider.Provider;

@WebServlet(urlPatterns = "/from/google-plus", loadOnStartup = 1)
public class GoogleSignUpController extends AbstractSignUpController {
	private static final long serialVersionUID = 1L;

	@Override
	protected Provider getProvider() {
		return Provider.GOOGLE;
	}

}
