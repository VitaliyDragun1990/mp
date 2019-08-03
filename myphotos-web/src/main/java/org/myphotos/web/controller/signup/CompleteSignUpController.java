package org.myphotos.web.controller.signup;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.myphotos.domain.entity.Profile;
import org.myphotos.security.SecurityManager;
import org.myphotos.validation.group.SignUpGroup;
import org.myphotos.web.controller.AbstractSaveProfileController;
import org.myphotos.web.security.SecurityUtils;

@WebServlet(urlPatterns = "/sign-up/complete", loadOnStartup = 1)
public class CompleteSignUpController extends AbstractSaveProfileController {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SecurityManager securityManager;

	@Override
	protected Class<?>[] getValidationGroups() {
		return new Class<?>[] {SignUpGroup.class};
	}

	@Override
	protected String getBackToEditView() {
		return "sign-up";
	}

	@Override
	protected Profile getCurrentProfile(HttpServletRequest req) {
		return (Profile) req.getSession().getAttribute("signUpProfile");
	}

	@Override
	protected void saveProfile(HttpServletRequest req, Profile profile) {
		securityManager.completeActiveSignUp();
		req.getSession().removeAttribute("signUpProfile");
		reloginWithUserRole(profile);
	}

	private void reloginWithUserRole(Profile profile) {
		SecurityUtils.logout();
		SecurityUtils.authenticate(profile);
	}

}
