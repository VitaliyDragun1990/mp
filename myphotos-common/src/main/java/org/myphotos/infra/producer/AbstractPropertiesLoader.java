package org.myphotos.infra.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Vetoed;

import org.myphotos.infra.resource.ResourceLoaderManager;

@Vetoed
abstract class AbstractPropertiesLoader {

	protected Logger logger;
	protected ResourceLoaderManager resourceLoaderManager;

	protected void loadProperties(Properties properties, String resourceName) {
		try {
			try (InputStream in = resourceLoaderManager.getResourceInputStream(resourceName)) {
				properties.load(in);
			}
			logger.log(Level.INFO, "Successfully loaded properties from {0}", resourceName);
		} catch (IOException | RuntimeException ex) {
			logger.log(Level.WARNING, "Can't load properties from: " + resourceName, ex);
		}
	}
}
