package org.myphotos.rest.security;

import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.rest.model.SignUpProfileREST;
import org.myphotos.rest.model.SimpleProfileREST;

public interface AuthenticationManager {

	AuthenticationResult signIn(String authenticationCode, Provider socialProvider);

	AuthenticationResult signUp(SignUpProfileREST signUpProfile);
	
	void signOut(String accessToken);

	public static class AuthenticationResult {
		private final String accessToken;
		private final SimpleProfileREST profile;
		private final boolean isAuthenticated;
		
		public static AuthenticationResult success(String accessToken, SimpleProfileREST profile) {
			return new AuthenticationResult(accessToken, profile, true);
		}
		
		public static AuthenticationResult fail(SimpleProfileREST profile) {
			return new AuthenticationResult(null, profile, false);
		}

		private AuthenticationResult(String accessToken, SimpleProfileREST profile, boolean isAuthenticated) {
			this.accessToken = accessToken;
			this.profile = profile;
			this.isAuthenticated = isAuthenticated;
		}

		public String getAccessToken() {
			return accessToken;
		}

		public SimpleProfileREST getProfile() {
			return profile;
		}

		public boolean isAuthenticated() {
			return isAuthenticated;
		}

	}
}