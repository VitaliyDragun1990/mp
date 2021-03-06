package org.myphotos.web.security;

import org.apache.shiro.subject.Subject;
import org.myphotos.domain.entity.Profile;
import org.myphotos.web.security.authentication.ProfileAuthenticationToken;
import org.myphotos.web.security.authentication.TempAuthenticationToken;
import org.myphotos.web.security.exception.AuthenticationException;
import org.myphotos.web.security.model.AuthUser;

/**
 * Static utility methods to provide user authentication in explicit way.
 * 
 * @author Vitaliy Dragun
 *
 */
public class SecurityUtils {

	public static final String TEMP_PROFILE = "";

	public static final String TEMP_PASS = "";

	public static final String TEMP_ROLE = "TEMP";

	public static final String PROFILE_ROLE = "PROFILE";

	/**
	 * Logs out currently authenticated user
	 */
	public static void logout() {
		Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
		currentSubject.logout();
	}

	/**
	 * Authenticate user represented by specified {@link Profile} parameter
	 * 
	 * @param profile profile of the user to authenticate
	 */
	public static void authenticate(Profile profile) {
		Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
		currentSubject.login(new ProfileAuthenticationToken(profile));
	}

	/**
	 * Provide temporary authentication to the current anonymous user
	 */
	public static void authenticateTemporary() {
		Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
		currentSubject.login(new TempAuthenticationToken());
	}

	/**
	 * Checks whether current user is a fully authenticated one
	 */
	public static boolean isAuthenticated() {
		Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
		return currentSubject.isAuthenticated();
	}

	/**
	 * Checks whether current user is temporary authenticated one
	 */
	public static boolean isTempAuthenticated() {
		Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
		return currentSubject.isAuthenticated() && TEMP_PROFILE.equals(currentSubject.getPrincipal());
	}

	/**
	 * Returns {@link Profile} object of the currently authenticated user
	 * 
	 * @throws AuthenticationException if current user is not authenticated
	 */
	public static AuthUser getAuthenticatedUser() {
		Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
		if (currentSubject.isAuthenticated()) {
			return (AuthUser) currentSubject.getPrincipal();
		} else {
			throw new AuthenticationException("Current user is not authenticated");
		}
	}

	private SecurityUtils() {
	}
}
