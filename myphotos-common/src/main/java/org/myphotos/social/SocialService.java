package org.myphotos.social;

import org.myphotos.domain.entity.Profile;
import org.myphotos.social.exception.SocialDataRetrievingException;

/**
 * Allows to sign-up/sign-in users via their social network accounts
 * 
 * @author Vitaliy Dragun
 *
 */
public interface SocialService {

	/**
	 * Fetch user's profile from social network of some sort.
	 * 
	 * @param code verification code/token to getch profile data
	 * @return {@link Profile} populated with data from some social network account
	 * @throws SocialDataRetrievingException if error occurs while retrieving data
	 *                                       from social network acount
	 */
	Profile fetchProfile(String code) throws SocialDataRetrievingException;
	
	/**
	 * Returns special authorize url user should follow to authenticate themselves
	 * via their social network account.
	 */
	default String getAuthorizeUrl() {
		throw new UnsupportedOperationException();
	}
}
