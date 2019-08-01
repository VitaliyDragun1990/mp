package org.myphotos.security;

import static org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;

import java.util.Optional;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.exception.business.ObjectNotFoundException;

/**
 * Responsible for managing sign up process
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public interface SignUpProcessManager {

	/**
	 * Tries to sign up a user using specified {@code authCode} and given social
	 * network {@code provider}. If no registered {@link Profile} was found for
	 * specified user's {@code authCode} parameter, then new sign-up process
	 * instance of {@link SignUpProcess} will be started.
	 * 
	 * @param authToken authentication token to authenticate user
	 * @param provider  {@link Provider} that denotes some kind of social network
	 *                  provider
	 * @return {@link Optional} containing authenticated user's {@link Profile}, or
	 *         empty one if there is no registered profile fo such user.
	 */
	Optional<Profile> tryToSignUp(String authToken, Provider provider);

	/**
	 * Returns profile that currently takes part in the active sign up process.
	 * 
	 * @throws ObjectNotFoundException if no profile taking part in the sign up
	 *                                 process found.
	 */
	Profile getSignUpProfile();

	/**
	 * Completes active sign up process, registering new profile in the application.
	 * 
	 * @throws InvalidWorkFlowException if no active sign-up process found
	 */
	void completeActiveSignUp();

	/**
	 * Cancels currently active sign up process, discarding all changes made up to
	 * this point in such sign up process
	 * 
	 * @throws InvalidWorkFlowException if no active sign-up process found
	 */
	void cancelActiveSignUp();
}
