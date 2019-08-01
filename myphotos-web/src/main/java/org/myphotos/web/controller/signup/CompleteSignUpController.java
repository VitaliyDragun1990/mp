package org.myphotos.web.controller.signup;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import org.myphotos.domain.entity.Profile;
import org.myphotos.security.SignUpProcessManager;
import org.myphotos.validation.group.SignUpGroup;
import org.myphotos.web.controller.AbstractSaveProfileController;

@WebServlet(urlPatterns = "/sign-up/complete", loadOnStartup = 1)
public class CompleteSignUpController extends AbstractSaveProfileController {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SignUpProcessManager signUpProcessManager;

	@Override
	protected Class<?>[] getValidationGroups() {
		return new Class<?>[] {SignUpGroup.class};
	}

	@Override
	protected String getBackToEditView() {
		return "sign-up";
	}

	@Override
	protected Profile getCurrentProfile() {
		return signUpProcessManager.getSignUpProfile();
	}

	@Override
	protected void saveProfile(Profile profile) {
		signUpProcessManager.completeActiveSignUp();
		reloginWithUserRole(profile);
	}

	private void reloginWithUserRole(Profile profile) {
		// TODO move this functionality to SignUpProcessManager
	}

}
