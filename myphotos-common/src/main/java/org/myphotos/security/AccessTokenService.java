package org.myphotos.security;

import org.myphotos.domain.entity.Profile;
import org.myphotos.security.exception.AccessForbiddenException;
import org.myphotos.security.exception.InvalidAccessTokenException;

/**
 * Contains access token related functionality.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface AccessTokenService {

	/**
	 * Generates {@link AccessToken} for given {@code profile}
	 * 
	 * @param profile {@link Profile} to generate access token for
	 * @return generated access token
	 */
	AccessToken generateAccessToken(Profile profile);

	/**
	 * Finds profile for given access {@code token} and {@code profileId}
	 * 
	 * @param token     access token to find profile for
	 * @param profileId id of the profile to find
	 * @return {@link Profile} suitable for given {@code token} and
	 *         {@code profileId} parameters
	 * @throws AccessForbiddenException    if specified {@code token} does not
	 *                                     belong to profile with provided
	 *                                     {@code profileId} id
	 * @throws InvalidAccessTokenException if specified {@code token} value is
	 *                                     invalid
	 */
	Profile findProfile(String token, Long profileId) throws AccessForbiddenException, InvalidAccessTokenException;

	/**
	 * Invalidates {@link AccessToken} with specified {@code token} value
	 * 
	 * @param token token value to invalidate
	 * @throws InvalidAccessTokenException if there is no {@link AccessToken} with
	 *                                     given {@code token} value to invalidate.
	 */
	void invalidateAccessToken(String token) throws InvalidAccessTokenException;
}
