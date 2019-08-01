package org.myphotos.infra.gateway.social;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.converter.TranslitConverter;
import org.myphotos.infra.cdi.annotation.Property;
import org.myphotos.infra.cdi.annotation.SocialProvider;
import org.myphotos.infra.cdi.annotation.SocialProvider.Provider;
import org.myphotos.infra.gateway.social.exception.SocialNetworkGatewayException;
import org.myphotos.infra.gateway.social.model.SocialNetworkAccount;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;

@SocialProvider(Provider.FACEBOOK)
@ApplicationScoped
class FacebookGateway implements SocialNetworkGateway {
	
	@Inject
	@Property("myphotos.social.facebook.client-id")
	private String appId;
	
	@Inject
	@Property("myphotos.social.facebook.client-secret")
	private String appSecret;
	
	@Inject
	@Property("myphotos.host")
	private String host;
	
	@Inject
	@Property("myphotos.social.facebook.redirectUrl")
	private String redirectUrl;
	
	@Inject
	private TranslitConverter translitConverter;

	@Override
	public String getAuthorizeUrl() {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(FacebookPermissions.EMAIL);
		FacebookClient client = new DefaultFacebookClient(Version.LATEST);
		return client.getLoginDialogUrl(appId, getRedirectUrl(), scopeBuilder);
	}

	@Override
	public SocialNetworkAccount getSocialNetworkAccount(String verificationCode) {
		try {
			User user = getFacebookUser(verificationCode);
			String avatarUrl = getFacebookUserAvatarUrl(user.getId());
			return buildSocialNetworkAccount(user, avatarUrl);
		} catch (FacebookException e) {
			throw new SocialNetworkGatewayException("Error while retrieving data from user's facebook account", e);
		}
	}
	
	private User getFacebookUser(String verificationCode) {
		FacebookClient client = new DefaultFacebookClient(Version.LATEST);
		AccessToken accessToken = client.obtainUserAccessToken(appId, appSecret, getRedirectUrl(), verificationCode);
		client = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
		return client.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name"));
	}

	private String getFacebookUserAvatarUrl(String facebookUserId) {
		return String.format("https://graph.facebook.com/v3.3/%s/picture?type=large", facebookUserId);
	}

	private SocialNetworkAccount buildSocialNetworkAccount(User user, String avatarUrl) {
		return new SocialNetworkAccount(
				translitConverter.translit(user.getFirstName()),
				translitConverter.translit(user.getLastName()),
				user.getEmail(),
				avatarUrl
				);
	}

	private String getRedirectUrl() {
		return host + redirectUrl;
	}

	void setAppId(String appId) {
		this.appId = appId;
	}

	void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	void setHost(String host) {
		this.host = host;
	}

	void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	void setTranslitConverter(TranslitConverter translitConverter) {
		this.translitConverter = translitConverter;
	}
	
}
