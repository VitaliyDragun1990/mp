package org.myphotos.security;

import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.infra.cdi.qualifier.SessionProxy;
import org.myphotos.infra.cdi.qualifier.SocialProvider;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.security.exception.SignUpProcessException;
import org.myphotos.social.SocialService;

@ApplicationScoped
class SignUpProcessManagerImpl implements SignUpProcessManager {

	@Inject
	private Map<Provider, AnnotationLiteral<SocialProvider>> socialProviderRegistry;

	@Inject
	@Any
	private Instance<SocialService> socialServices;

	@EJB
	private ProfileService profileService;

	@Inject
	@SessionProxy
	private SignUpProcess signUpProcess;

	@Override
	public Optional<Profile> tryToSignUp(String authToken, Provider provider) {
		SocialService socialService = getAppropriateSocialService(provider);
		Profile socialProfile = socialService.fetchProfile(authToken);
		Optional<Profile> registeredProfile = profileService.findByEmail(socialProfile.getEmail());
		if (registeredProfile.isPresent()) {
			// TODO: authenticate registered profile
		} else {
			// TODO: authenticate social profile ???
			signUpProcess.startSignUp(socialProfile);
		}
		return registeredProfile;
	}

	@Override
	public Profile getSignUpProfile() {
		return signUpProcess.getSignUpProfile();
	}

	@Override
	public void completeActiveSignUp() {
		signUpProcess.completeSignUp();
	}

	@Override
	public void cancelActiveSignUp() {
		signUpProcess.cancelSignUp();
	}

	private SocialService getAppropriateSocialService(Provider provider) {
		Instance<SocialService> socialService = socialServices.select(socialProviderRegistry.get(provider));
		if (socialService.isAmbiguous() || socialService.isUnsatisfied()) {
			throw new SignUpProcessException("Can't find appropriate SocialService to start sign up process");
		}
		return socialService.get();
	}

	void setSocialProviderRegistry(Map<Provider, AnnotationLiteral<SocialProvider>> socialProviderRegistry) {
		this.socialProviderRegistry = socialProviderRegistry;
	}

	void setSocialServices(Instance<SocialService> socialServices) {
		this.socialServices = socialServices;
	}

	void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	void setSignUpProcess(SignUpProcessProxy signUpProcess) {
		this.signUpProcess = signUpProcess;
	}

}
