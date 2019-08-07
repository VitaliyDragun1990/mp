package org.myphotos.converter;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.infra.cdi.qualifier.Property;

@ApplicationScoped
class DefaultAbsoluteUrlConverter implements AbsoluteUrlConverter {
	
	@Inject
	@Property("myphotos.host")
	private String host;

	@Override
	public String convert(String url) {
		return isAbsolute(url) ? url : (host + url);
	}
	
	private boolean isAbsolute(String url) {
		try {
			if (new URI(url).isAbsolute()) {
				return true;
			}
		} catch (URISyntaxException e) {
			// do nothing, Url is relative
		}
		return false;
	}
	
	void setHost(String host) {
		this.host = host;
	}

}
