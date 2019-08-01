package org.myphotos.web.controller.signup;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.infra.cdi.qualifier.SocialProvider;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.social.SocialService;
import org.myphotos.web.router.Router;

@WebServlet(urlPatterns = "/sign-up/facebook", loadOnStartup = 1)
public class FacebookSignUpRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	@SocialProvider(Provider.FACEBOOK)
	private SocialService socialService;
	@Inject
	private Router router;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		router.redirectToUrl(socialService.getAuthorizeUrl(), resp);
	}
}
