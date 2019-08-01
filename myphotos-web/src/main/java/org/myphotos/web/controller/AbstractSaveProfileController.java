package org.myphotos.web.controller;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.myphotos.domain.entity.Profile;
import org.myphotos.web.form.FormReader;
import org.myphotos.web.form.ProfileForm;
import org.myphotos.web.router.Router;
import org.myphotos.web.validation.ConstraintViolationConverter;

public abstract class AbstractSaveProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:comp/Validator")
	private Validator validator;
	
	@Inject
	private ConstraintViolationConverter constraintViolationConverter;
	
	@Inject
	private FormReader formReader;
	
	@Inject
	private Router router;
	
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProfileForm form = formReader.readForm(req, ProfileForm.class);
		Set<ConstraintViolation<ProfileForm>> violations = validator.validate(form, getValidationGroups());
		if (violations.isEmpty()) {
			saveChanges(form, resp);
		} else {
			backToEditPage(req, form, violations, resp);
		}
	}
	
	protected abstract Class<?>[] getValidationGroups();
	
	private void backToEditPage(HttpServletRequest req, ProfileForm form, Set<ConstraintViolation<ProfileForm>> violations,
			HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("profile", form);
		req.setAttribute("violations", constraintViolationConverter.convert(violations));
		router.forwardToPage(getBackToEditView(), req, resp);
	}

	protected abstract String getBackToEditView();
	
	private void saveChanges(ProfileForm form, HttpServletResponse resp) throws IOException {
		Profile profile = getCurrentProfile();
		form.copyToProfile(profile);
		saveProfile(profile);
		
		router.redirectToUrl("/" + profile.getUid(), resp);
	}

	protected abstract Profile getCurrentProfile();
	
	protected abstract void saveProfile(Profile profile);
}
