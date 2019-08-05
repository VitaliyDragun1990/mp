package org.myphotos.web.controller.profile;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.domain.entity.Profile;
import org.myphotos.web.router.Router;
import org.myphotos.web.security.SecurityUtils;

@WebServlet(urlPatterns = "/edit", loadOnStartup = 1)
public class EditProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Router router;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Profile profile = SecurityUtils.getAuthenticatedProfile();
		req.setAttribute("profile", profile);
		router.forwardToPage("edit", req, resp);
	}
}
