package org.myphotos.web.controller.signup;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.cdi.annotation.SessionProxy;
import org.myphotos.security.SignUpProcess;
import org.myphotos.web.router.Router;

@WebServlet(urlPatterns = "/sign-up", loadOnStartup = 1)
public class SignUpProgressController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	@SessionProxy
	private SignUpProcess signUpProcess;
	@Inject
	private Router router;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Profile signUpProfile = signUpProcess.getSignUpProfile();
		req.setAttribute("profile", signUpProfile);
		
		router.forwardToPage("sign-up", req, resp);
	}
}
