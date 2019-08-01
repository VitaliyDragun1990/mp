package org.myphotos.repository.jpa.listener;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.myphotos.infra.exception.business.InvalidWorkFlowException;
import org.myphotos.security.model.AccessToken;

public class AccessTokenLifeCycleManager {

	@Inject
	private Logger logger;
	
	@PrePersist
	public void setToken(AccessToken model) {
		model.setToken(UUID.randomUUID().toString().replace("-", ""));
		logger.log(Level.FINE, "Generated new uid token {0} for entity {1}", new Object[] {model.getToken(), model.getClass()});
	}
	
	@PreUpdate
	public void rejectUpdate(AccessToken model) {
		throw new InvalidWorkFlowException("AccessToken is not updetable");
	}
}
