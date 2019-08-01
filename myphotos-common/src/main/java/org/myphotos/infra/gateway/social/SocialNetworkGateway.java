package org.myphotos.infra.gateway.social;

import javax.enterprise.inject.Vetoed;

import org.myphotos.infra.gateway.social.model.SocialNetworkAccount;

/**
 * This interface represents component responsible for getting access to
 * client's account from some sort of social network.
 * 
 * @author Vitaly Dragun
 *
 */
@Vetoed
public interface SocialNetworkGateway {

	/**
	 * Optional operation: provider dependent.
	 * Returns authorization url to which clients should be redirected in order to
	 * authenticate themselves as users of some social network.
	 * 
	 */
	default String getAuthorizeUrl() {
		throw new UnsupportedOperationException("getAuthorizeUrl not supported by this social network profider");
	}
	
	/**
	 * Gets {@link SocialAccount} of authenticated social network user using
	 * provided {@code authToken} parameter.
	 * 
	 * @param verificationCode authentication token that will be used to get user's account
	 *                  in social network.
	 * @return {@link SocialAccount} instance that represents user's social network
	 *         account
	 * @throws SocialNetworkGatewayException if error occurs while getting information
	 *                                 about client's social network account
	 */
	SocialNetworkAccount getSocialNetworkAccount(String verificationCode);
}
