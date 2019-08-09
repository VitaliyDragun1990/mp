package org.myphotos.rest.security;

import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.myphotos.converter.ModelConverter;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.infra.cdi.qualifier.SocialProvider;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.rest.model.ProfileREST;
import org.myphotos.rest.model.SignUpProfileREST;
import org.myphotos.rest.model.SimpleProfileREST;
import org.myphotos.security.AccessTokenService;
import org.myphotos.security.exception.SignUpProcessException;
import org.myphotos.security.model.AccessToken;
import org.myphotos.social.SocialService;

@ApplicationScoped
class RESTAuthenticationManager implements AuthenticationManager {
	
	@Inject
	private Map<Provider, AnnotationLiteral<SocialProvider>> socialProviderRegistry;

	@Inject
	@Any
	private Instance<SocialService> socialServices;

	@EJB
	private ProfileService profileService;
	
	@EJB
	private AccessTokenService accessTokenService;
	
	@Inject
	private ModelConverter modelConverter;
	
	@Override
	public AuthenticationResult signIn(String authenticationCode, Provider socialProvider) {
		SocialService socialService = getAppropriateSocialService(socialProvider);
		Profile socialProfile = socialService.fetchProfile(authenticationCode);
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
	
	private SocialService getAppropriateSocialService(Provider provider) {
		Instance<SocialService> socialService = socialServices.select(socialProviderRegistry.get(provider));
		if (socialService.isAmbiguous() || socialService.isUnsatisfied()) {
			throw new SignUpProcessException("Can't find appropriate SocialService to start sign up process");
		}
		return socialService.get();
	}

	Map<Provider, AnnotationLiteral<SocialProvider>> getSocialProviderRegistry() {
		return socialProviderRegistry;
	}

	void setSocialProviderRegistry(Map<Provider, AnnotationLiteral<SocialProvider>> socialProviderRegistry) {
		this.socialProviderRegistry = socialProviderRegistry;
	}

	Instance<SocialService> getSocialServices() {
		return socialServices;
	}

	void setSocialServices(Instance<SocialService> socialServices) {
		this.socialServices = socialServices;
	}

	ProfileService getProfileService() {
		return profileService;
	}

	void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	AccessTokenService getAccessTokenService() {
		return accessTokenService;
	}

	void setAccessTokenService(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}
	
	
}
