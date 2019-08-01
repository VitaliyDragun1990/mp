package org.myphotos.security;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.inject.Inject;

import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.infra.exception.business.ObjectNotFoundException;

@Stateful
@StatefulTimeout(value = 30, unit = TimeUnit.MINUTES)
public class SignUpProcessBean implements SignUpProcess, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Profile profile;
	
	@Inject
	private transient Logger logger;
	
	@Inject
	private transient ProfileService profileService;

	@Override
	public void startSignUp(Profile profile) {
		this.profile = profile;
	}

	@Override
	public Profile getSignUpProfile() throws ObjectNotFoundException {
		if (profile == null) {
			throw new ObjectNotFoundException("Profile not found. Please create profile by calling startSignUp()");
		}
		return profile;
	}

	@Remove
	@Override
	public void completeSignUp() {
		profileService.create(profile, false);
	}

	@Remove
	@Override
	public void cancelSignUp() {
		this.profile = null;
	}
	
	@PostConstruct
	private void init() {
		logger.log(Level.FINE, "Created {0} instance: {1}",
				new Object[] {getClass().getSimpleName(), System.identityHashCode(this)});
	}
	
	@PreDestroy
	private void cleanUp() {
		logger.log(Level.FINE, "Destroyed {0} instance: {1}",
				new Object[] {getClass().getSimpleName(), System.identityHashCode(this)});
	}
	
	@PostActivate
	private void postActivate() {
		logger.log(Level.FINE, "Activated {0} instance: {1}",
				new Object[] {getClass().getSimpleName(), System.identityHashCode(this)});
	}
	
	@PrePassivate
	private void prePassivate() {
		logger.log(Level.FINE, "Passivated {0} instance: {1}",
				new Object[] {getClass().getSimpleName(), System.identityHashCode(this)});
	}
	
	void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	void setLogger(Logger logger) {
		this.logger = logger;
	}

}
