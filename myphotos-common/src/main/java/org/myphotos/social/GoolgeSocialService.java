package org.myphotos.social;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.infra.cdi.qualifier.SocialProvider;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.infra.gateway.social.SocialNetworkGateway;

@SocialProvider(Provider.GOOGLE)
@ApplicationScoped
class GoolgeSocialService extends AbstractSocialService {

	@Inject
	@Override
	protected void setSocialNetworkGateway(@SocialProvider(Provider.GOOGLE) SocialNetworkGateway socialNetworkGateway) {
		this.socialNetworkGateway = socialNetworkGateway;
	}
}
