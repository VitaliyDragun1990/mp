package org.myphotos.web.security;

import static org.myphotos.web.security.SecurityUtils.TEMP_PASS;

import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.myphotos.domain.entity.Profile;

/**
 * Used for permanent user authentication
 * 
 * @author Vitaliy Dragun
 *
 */
public class ProfileAuthenticationToken implements RememberMeAuthenticationToken {
	private static final long serialVersionUID = 1L;

	private final Profile profile;

	public ProfileAuthenticationToken(Profile profile) {
		this.profile = profile;
	}

	@Override
	public Object getPrincipal() {
		return profile;
	}

	@Override
	public Object getCredentials() {
		return TEMP_PASS;
	}

	@Override
	public boolean isRememberMe() {
		return false;
	}

}
