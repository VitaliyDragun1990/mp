package org.myphotos.ejb.service.domain;

import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.myphotos.ejb.repository.jpa.DBSource;
import org.myphotos.infra.repository.ProfileRepository;

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
