package org.myphotos.infra.gateway.social;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.infra.cdi.qualifier.Property;
import org.myphotos.infra.cdi.qualifier.SocialProvider;
import org.myphotos.infra.cdi.qualifier.SocialProvider.Provider;
import org.myphotos.infra.gateway.social.exception.SocialNetworkGatewayException;
import org.myphotos.infra.gateway.social.model.SocialNetworkAccount;
import org.myphotos.infra.util.CommonUtils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@SocialProvider(Provider.GOOGLE)
@ApplicationScoped
class GoogleGateway implements SocialNetworkGateway {
	
	private String clientId;
	private List<String> issuers;

	@Override
	public SocialNetworkAccount getSocialNetworkAccount(String authToken) {
		try {
			return obtainSocialAccount(authToken);
		} catch (GeneralSecurityException | IOException e) {
			throw new SocialNetworkGatewayException("Error while retrieving data from user's Goolge account", e);
		}
	}

	private SocialNetworkAccount obtainSocialAccount(String authToken) throws GeneralSecurityException, IOException {
		GoogleIdTokenVerifier verifier = buildTokenVerifier();
		GoogleIdToken idToken = verifier.verify(authToken);
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			return new SocialNetworkAccount(
					(String) payload.get("given_name"),
					(String) payload.get("family_name"),
					payload.getEmail(),
					(String) payload.get("picture"));
		} else {
			throw new SocialNetworkGatewayException("Can not get access to user's Google account using authToken: " + authToken);
		}
	}

	private GoogleIdTokenVerifier buildTokenVerifier() {
		return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
				.setAudience(Collections.singleton(clientId))
				.setIssuers(issuers)
				.build();
	}
	
	@Inject
	void setClientId(@Property("myphotos.social.google-plus.client-id") String clientId) {
		this.clientId = clientId;
	}
	
	@Inject
	void setIssuers(@Property("myphotos.social.google-plus.issuers") String issuers) {
		this.issuers = CommonUtils.getSafeList(Arrays.asList(issuers.split(",")));
	}

}
