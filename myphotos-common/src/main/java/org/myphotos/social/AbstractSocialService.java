package org.myphotos.social;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.gateway.social.SocialNetworkGateway;
import org.myphotos.infra.gateway.social.exception.SocialNetworkGatewayException;
import org.myphotos.infra.gateway.social.model.SocialNetworkAccount;
import org.myphotos.social.exception.SocialDataRetrievingException;

@Vetoed
abstract class AbstractSocialService implements SocialService {
	
	protected SocialNetworkGateway socialNetworkGateway;
	
	protected abstract void setSocialNetworkGateway(SocialNetworkGateway socialNetworkGateway);

	@Override
	public final Profile fetchProfile(String code) throws SocialDataRetrievingException {
		try {
			SocialNetworkAccount networkAccount = socialNetworkGateway.getSocialNetworkAccount(code);
			return createProfile(networkAccount);
		} catch (SocialNetworkGatewayException e) {
			throw new SocialDataRetrievingException("Can't fetch user from social network: " + e.getMessage(), e);
		}
	}
	
	private Profile createProfile(SocialNetworkAccount networkAccount) {
		Profile profile = new Profile();
		profile.setEmail(networkAccount.getEmail());
		profile.setFirstName(networkAccount.getFirstName());
		profile.setLastName(networkAccount.getLastName());
		profile.setAvatarUrl(networkAccount.getAvatarUrl());
		return profile;
	}

}
