package org.myphotos.web.security;

import static org.myphotos.web.security.SecurityUtils.TEMP_PASS;
import static org.myphotos.web.security.SecurityUtils.TEMP_PROFILE;
import static org.myphotos.web.security.SecurityUtils.TEMP_ROLE;

import java.util.Collections;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.myphotos.web.security.authentication.TempAuthenticationToken;

public class TempRealm extends AuthorizingRealm {
	
	private static final Account ACCOUNT = new SimpleAccount(
			new SimplePrincipalCollection(TEMP_PROFILE, TempRealm.class.getSimpleName()),
			TEMP_PASS,
			Collections.singleton(TEMP_ROLE));
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof TempAuthenticationToken;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		return ACCOUNT;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (TEMP_PROFILE.equals(principals.getPrimaryPrincipal())) {
			return ACCOUNT;
		} else {
			return null;
		}
	}

}
