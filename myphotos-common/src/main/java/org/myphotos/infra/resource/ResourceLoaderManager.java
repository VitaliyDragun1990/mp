package org.myphotos.infra.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.myphotos.infra.exception.ConfigurationException;

@ApplicationScoped
public class ResourceLoaderManager {
	private Instance<ResourceLoader> resourceLoaders;

	@Inject
	@Any 
	protected void setResourceLoaders(Instance<ResourceLoader> resourceLoaders) {
		this.resourceLoaders = resourceLoaders;
	}
	
	public InputStream getResourceInputStream(String resourceName) throws IOException {
		for (ResourceLoader resourceLoader : resourceLoaders) {
			if (resourceLoader.isSupport(resourceName)) {
				return resourceLoader.getInputStream(resourceName);
			}
		}
	
		throw new ConfigurationException("Can't get input stream for resource: " + resourceName);
	}
}
