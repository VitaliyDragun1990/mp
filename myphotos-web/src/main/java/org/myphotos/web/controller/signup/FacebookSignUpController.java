package org.myphotos.web.controller.signup;

import javax.servlet.annotation.WebServlet;

import org.myphotos.infra.cdi.annotation.SocialProvider.Provider;

@WebServlet(urlPatterns = "/from/facebook", loadOnStartup = 1)
public class FacebookSignUpController extends AbstractSignUpController {
	private static final long serialVersionUID = 1L;

	@Override
	protected Provider getProvider() {
		return Provider.FACEBOOK;
	}

}
