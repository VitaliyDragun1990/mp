package org.myphotos.rest.security;

import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.converter.ModelConverter;
import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.rest.model.ProfileREST;
import org.myphotos.rest.model.SignUpProfileREST;
import org.myphotos.rest.model.SimpleProfileREST;
import org.myphotos.security.AccessTokenService;
import org.myphotos.security.BaseAuthenticationManager;
import org.myphotos.security.model.AccessToken;

@ApplicationScoped
class RESTAuthenticationManager extends BaseAuthenticationManager implements AuthenticationManager {
	
	@EJB
	private AccessTokenService accessTokenService;
	
	@Inject
	private ModelConverter modelConverter;
	
	@Override
	public AuthenticationResult signIn(String authenticationCode, Provider socialProvider) {;
		Profile socialProfile = fetchSocialProfile(authenticationCode, socialProvider);
		Optional<Profile> profileOptional = profileService.findByEmail(socialProfile.getEmail());
		
		if (profileOptional.isPresent()) {
			Profile signedInProfile = profileOptional.get();
			AccessToken accessToken = accessTokenService.generateAccessToken(signedInProfile);
			
			return AuthenticationResult.success(
					accessToken.getToken(),
					modelConverter.convert(signedInProfile, SimpleProfileREST.class)
					);
		} else {
			return AuthenticationResult.fail(modelConverter.convert(socialProfile, ProfileREST.class));
		}
	}
	
	@Override
	public AuthenticationResult signUp(SignUpProfileREST signUpProfile) {
		Profile profile = new Profile();
		signUpProfile.copyToProfile(profile);
		profileService.create(profile, true);
		AccessToken accessToken = accessTokenService.generateAccessToken(profile);
		
		return AuthenticationResult.success(
				accessToken.getToken(),
				modelConverter.convert(profile, SimpleProfileREST.class)
				);
	}
	
	@Override
	public void signOut(String accessToken) {
		accessTokenService.invalidateAccessToken(accessToken);
	}

	void setAccessTokenService(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}
	
	
}
