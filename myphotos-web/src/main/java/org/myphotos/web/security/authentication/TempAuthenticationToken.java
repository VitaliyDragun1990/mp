package org.myphotos.web.security.authentication;

import static org.myphotos.web.security.SecurityUtils.TEMP_PASS;
import static org.myphotos.web.security.SecurityUtils.TEMP_PROFILE;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Used for temporary user authentication
 * 
 * @author Vitaliy Dragun
 *
 */
public class TempAuthenticationToken implements AuthenticationToken {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getPrincipal() {
		return TEMP_PROFILE;
	}

	@Override
	public Object getCredentials() {
		return TEMP_PASS;
	}

}
