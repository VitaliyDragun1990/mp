package org.myphotos.social;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.infra.cdi.annotation.SocialProvider;
import org.myphotos.infra.cdi.annotation.SocialProvider.Provider;
import org.myphotos.infra.gateway.social.SocialNetworkGateway;

@SocialProvider(Provider.FACEBOOK)
@ApplicationScoped
class FacebookSocialService extends AbstractSocialService {

	@Inject
	@Override
	protected void setSocialNetworkGateway(
			@SocialProvider(Provider.FACEBOOK) SocialNetworkGateway socialNetworkGateway) {
		this.socialNetworkGateway = socialNetworkGateway;
	}

	@Override
	public String getAuthorizeUrl() {
		return socialNetworkGateway.getAuthorizeUrl();
	}

}
