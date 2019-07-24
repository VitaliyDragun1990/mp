package org.myphotos.infra.producer;

import java.util.Properties;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.myphotos.infra.cdi.annotation.PropertiesSource;
import org.myphotos.infra.resource.ResourceLoaderManager;

@Dependent
public class PropertiesSourceProducer extends AbstractPropertiesLoader {
	
	@Inject
	protected void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@Inject
	protected void setResourceLoaderManager(ResourceLoaderManager resourceLoaderManager) {
		this.resourceLoaderManager = resourceLoaderManager;
	}

	@Produces
	@PropertiesSource("")
	private Properties loadProperties(InjectionPoint injectionPoint) {
		Properties properties = new Properties();
		PropertiesSource propertySource = injectionPoint.getAnnotated().getAnnotation(PropertiesSource.class);
		loadProperties(properties, propertySource.value());
		return properties;
	}
}
