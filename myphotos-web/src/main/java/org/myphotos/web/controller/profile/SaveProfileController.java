package org.myphotos.web.controller.profile;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.validation.group.ProfileUpdateGroup;
import org.myphotos.web.controller.AbstractSaveProfileController;
import org.myphotos.web.security.SecurityUtils;

@WebServlet(urlPatterns = "/save", loadOnStartup = 1)
public class SaveProfileController extends AbstractSaveProfileController {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProfileService profileService;

	@Override
	protected Class<?>[] getValidationGroups() {
		return new Class<?>[] {ProfileUpdateGroup.class};
	}

	@Override
	protected String getBackToEditView() {
		return "edit";
	}

	@Override
	protected Profile getCurrentProfile(HttpServletRequest req) {
		return SecurityUtils.getAuthenticatedProfile();
	}

	@Override
	protected void saveProfile(HttpServletRequest req, Profile profile) {
		profileService.update(profile);
	}

}
