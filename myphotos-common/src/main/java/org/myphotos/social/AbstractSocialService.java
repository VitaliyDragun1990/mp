package org.myphotos.social;

import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;

import org.myphotos.converter.TranslitConverter;
import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.gateway.social.SocialNetworkGateway;
import org.myphotos.infra.gateway.social.exception.SocialNetworkGatewayException;
import org.myphotos.infra.gateway.social.model.SocialNetworkAccount;
import org.myphotos.social.exception.SocialDataRetrievingException;

@Vetoed
abstract class AbstractSocialService implements SocialService {
	
	private TranslitConverter translitConverter;
	protected SocialNetworkGateway socialNetworkGateway;
	
	@Inject
	void setTranslitConverter(TranslitConverter translitConverter) {
		this.translitConverter = translitConverter;
	}
	
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
		profile.setFirstName(networkAccount.getFirstName() != null ? translitConverter.translit(networkAccount.getFirstName()) : null);
		profile.setLastName(networkAccount.getLastName() != null ? translitConverter.translit(networkAccount.getLastName()) : null);
		profile.setAvatarUrl(networkAccount.getAvatarUrl());
		return profile;
	}

}
