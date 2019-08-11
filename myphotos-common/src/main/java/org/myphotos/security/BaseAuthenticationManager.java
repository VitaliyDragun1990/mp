package org.myphotos.security;

import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.infra.cdi.qualifier.SocialProvider;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.security.exception.SignUpProcessException;
import org.myphotos.social.SocialService;

public abstract class BaseAuthenticationManager {

	@Inject
	private Map<Provider, AnnotationLiteral<SocialProvider>> socialProviderRegistry;

	@Inject
	@Any
	private Instance<SocialService> socialServices;

	@EJB
	protected ProfileService profileService;


	protected Profile fetchSocialProfile(String authToken, Provider provider) {
		SocialService socialService = getAppropriateSocialService(provider);
		return socialService.fetchProfile(authToken);
	}

	private SocialService getAppropriateSocialService(Provider provider) {
		Instance<SocialService> socialService = socialServices.select(socialProviderRegistry.get(provider));
		if (socialService.isAmbiguous() || socialService.isUnsatisfied()) {
			throw new SignUpProcessException("Can't find appropriate SocialService to start authentication process");
		}
		return socialService.get();
	}

	protected void setSocialProviderRegistry(Map<Provider, AnnotationLiteral<SocialProvider>> socialProviderRegistry) {
		this.socialProviderRegistry = socialProviderRegistry;
	}

	protected void setSocialServices(Instance<SocialService> socialServices) {
		this.socialServices = socialServices;
	}

	protected void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}


}
