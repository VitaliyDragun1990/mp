package org.myphotos.web.security.authentication;

import static org.myphotos.web.security.SecurityUtils.TEMP_PASS;

import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.myphotos.domain.entity.Profile;
import org.myphotos.web.security.model.AuthUser;

/**
 * Used for permanent user authentication
 * 
 * @author Vitaliy Dragun
 *
 */
public class ProfileAuthenticationToken implements RememberMeAuthenticationToken {
	private static final long serialVersionUID = 1L;

	private final AuthUser profile;

	public ProfileAuthenticationToken(Profile profile) {
		this.profile = new AuthUser(profile.getId(), profile.getUid(), profile.getEmail());
	}

	@Override
	public AuthUser getPrincipal() {
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
