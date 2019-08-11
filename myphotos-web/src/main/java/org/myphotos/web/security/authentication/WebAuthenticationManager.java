package org.myphotos.web.security.authentication;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.cdi.qualifier.SessionProxy;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.security.BaseAuthenticationManager;
import org.myphotos.security.SignUpProcess;
import org.myphotos.security.exception.SignUpProcessException;

@ApplicationScoped
class WebAuthenticationManager extends BaseAuthenticationManager implements AuthenticationManager {

	@Inject
	@SessionProxy
	private SignUpProcess signUpProcess;

	@Override
	public Optional<Profile> signIn(String authToken, Provider provider) {
		Profile socialProfile = fetchSocialProfile(authToken, provider);
		return profileService.findByEmail(socialProfile.getEmail());
	}

	@Override
	public Profile startSignUp(String authToken, Provider provider) {
		Profile socialProfile = fetchSocialProfile(authToken, provider);
		Optional<Profile> registeredProfile = profileService.findByEmail(socialProfile.getEmail());
		if (registeredProfile.isPresent()) {
			throw new SignUpProcessException(
					"Can't start sign up process for specified token: there is registered profile"
							+ "for this token already. Please use it to authenticate instead");
		}
		signUpProcess.startSignUp(socialProfile);
		return socialProfile;
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

	void setSignUpProcess(SignUpProcessProxy signUpProcess) {
		this.signUpProcess = signUpProcess;
	}

}
