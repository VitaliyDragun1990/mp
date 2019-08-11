package org.myphotos.web.security.authentication;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.cdi.qualifier.SessionProxy;
import org.myphotos.infra.exception.business.InvalidWorkFlowException;
import org.myphotos.security.SignUpProcess;

@SessionScoped
@SessionProxy
class SignUpProcessProxy implements SignUpProcess, Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private SignUpProcess originalSignUpProcess;
	
	@Inject
	private transient Logger logger;

	@Override
	public void startSignUp(Profile profile) {
		validate();
		originalSignUpProcess.startSignUp(profile);
	}

	@Override
	public Profile getSignUpProfile() {
		validate();
		return originalSignUpProcess.getSignUpProfile();
	}

	@Override
	public void completeSignUp() {
		validate();
		originalSignUpProcess.completeSignUp();
		originalSignUpProcess = null;
	}

	@Override
	public void cancelSignUp() {
		originalSignUpProcess.cancelSignUp();
		originalSignUpProcess = null;
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
		if (originalSignUpProcess != null) {
			originalSignUpProcess.cancelSignUp();
			originalSignUpProcess = null;
		}
	}
	
	private void validate() {
		if (originalSignUpProcess == null) {
			throw new InvalidWorkFlowException("Can't user SignUpProcess after completeSignUp() or cancelSignUp() has been invoked");
		}
	}
	
	void setOriginalSignUpProcess(SignUpProcess originalSignUpProcess) {
		this.originalSignUpProcess = originalSignUpProcess;
	}
	
	void setLogger(Logger logger) {
		this.logger = logger;
	}

}
