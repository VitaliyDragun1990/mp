package org.myphotos.security;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.exception.business.ObjectNotFoundException;

/**
 * Represents sign-up process.
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public interface SignUpProcess {

	/**
	 * Starts sign up process for new application user
	 * 
	 * @param profile data holder with information to sign up new user
	 */
	void startSignUp(Profile profile);

	/**
	 * Returns profile that currently takes part in the active sign up process.
	 * 
	 * @throws ObjectNotFoundException if no profile taking part in the sign up
	 *                                 process found.
	 */
	Profile getSignUpProfile() throws ObjectNotFoundException;

	/**
	 * Completes sign up process, persisting new profile in the data storage.
	 */
	void completeSignUp();

	/**
	 * Cancels currently active sign up process, discarding all changes made up to
	 * this point in such sign up process
	 */
	void cancelSignUp();
}
