package org.myphotos.web.security;

import static org.myphotos.web.security.SecurityUtils.PROFILE_ROLE;

import java.util.Collections;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.myphotos.domain.entity.Profile;

public class ProfileRealm extends AuthorizingRealm {

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof ProfileAuthenticationToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals.getPrimaryPrincipal() instanceof Profile) {
			return new SimpleAuthorizationInfo(Collections.singleton(PROFILE_ROLE));
		} else {
			return null;
		}
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		ProfileAuthenticationToken token = (ProfileAuthenticationToken) authenticationToken;
		return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), ProfileRealm.class.getSimpleName());
	}
	
	@Override
	protected Object getAvailablePrincipal(PrincipalCollection principals) {
		Object principal = super.getAvailablePrincipal(principals);
		if (principal instanceof Profile) {
			return ((Profile)principal).getEmail();
		}
		return principal;
	}
}
