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
public interface SecurityManager {

	/**
	 * Tries to sign in using specified {@code token} and given
	 * social network {@code provider}.
	 * 
	 * @param token token to provide user sign in
	 * @param provider  {@link Provider} that denotes some kind of social network
	 *                  provider
	 * @return {@link Optional} containing authenticated user's {@link Profile}, or
	 *         empty one if there is no registered profile fo such user.
	 */
	Optional<Profile> signIn(String token, Provider provider);

	/**
	 * Tries to start new sign up process to register a new user using specified
	 * {@code token} and given social network {@code provider}. This method
	 * doesn't provide cmpleted signup capability, it only starts sign up process an
	 * populate new {@link Profile} instance with data fetched from social network
	 * provider. To complete sign up process {@link #completeActiveSignUp()} must be
	 * called.
	 * 
	 * @param token token to provide sign up for new user
	 * @param provider  {@link Provider} that denotes some kind of social network
	 *                  provider
	 * @return {@link Profile} partially populated profile instance with information
	 *         fetched from social network provider
	 */
	Profile startSignUp(String token, Provider provider);

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
