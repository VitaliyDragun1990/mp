package org.myphotos.web.controller.signup;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.domain.entity.Profile;
import org.myphotos.security.SignUpProcessManager;
import org.myphotos.web.form.ProfileForm;
import org.myphotos.web.router.Router;

@WebServlet(urlPatterns = "/sign-up", loadOnStartup = 1)
public class SignUpProgressController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private SignUpProcessManager signUpProcessManager;
	@Inject
	private Router router;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Profile signUpProfile = signUpProcessManager.getSignUpProfile();
		req.setAttribute("profile", new ProfileForm(signUpProfile));
		
		router.forwardToPage("sign-up", req, resp);
	}
}
