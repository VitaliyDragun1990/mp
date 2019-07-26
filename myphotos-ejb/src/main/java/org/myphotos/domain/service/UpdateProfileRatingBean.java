package org.myphotos.domain.service;

import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.myphotos.infra.repository.ProfileRepository;
import org.myphotos.repository.jpa.DBSource;

@Stateless
public class UpdateProfileRatingBean {

	@Inject
	private Logger logger;
	
	@Inject
	@DBSource
	private ProfileRepository profileRepository;
	
	@Schedule(hour = "0", minute = "0", second = "0", persistent = false)
	public void updateProfileRating() {
		profileRepository.updateRating();
		logger.info("Profiles rating have been successfully updated");
	}
}
