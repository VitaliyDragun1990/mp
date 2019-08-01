package org.myphotos.infra.producer;

import java.util.EnumMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Vetoed;
import javax.enterprise.util.AnnotationLiteral;

import org.myphotos.infra.cdi.annotation.SocialProvider;
import org.myphotos.infra.cdi.annotation.SocialProvider.Provider;

@Dependent
public class SocialProviderProducer {

	@Produces
	public Map<Provider, AnnotationLiteral<SocialProvider>> getSocialProviderRegistry() {
		Map<Provider, AnnotationLiteral<SocialProvider>> providersMap = new EnumMap<>(Provider.class);
		providersMap.put(Provider.FACEBOOK, new FacebookSocialProvider());
		providersMap.put(Provider.GOOGLE, new GoogleSocialProvider());
		return providersMap;
	}
	
	@Vetoed
	static class GoogleSocialProvider extends AnnotationLiteral<SocialProvider> implements SocialProvider {
		private static final long serialVersionUID = 1L;

		@Override
		public Provider value() {
			return Provider.GOOGLE;
		}
	}
	
	@Vetoed
	static class FacebookSocialProvider extends AnnotationLiteral<SocialProvider> implements SocialProvider {
		private static final long serialVersionUID = 1L;

		@Override
		public Provider value() {
			return Provider.FACEBOOK;
		}
	}
}
