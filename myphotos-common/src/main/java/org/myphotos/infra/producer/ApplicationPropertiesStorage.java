package org.myphotos.infra.producer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.infra.resource.ResourceLoaderManager;

@ApplicationScoped
public class ApplicationPropertiesStorage extends AbstractPropertiesLoader {
	private static final String APPLICATION_CONFIG_FILE = "MYPHOTOS_CONFIG_FILE";
	private static final String APPLICATION_CONFIG_PROPERTY_PREFIX = "myphotos.";

	private final Properties applicationProeprties = new Properties();
	
	@Inject
	protected void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@Inject
	protected void setResourceLoaderManager(ResourceLoaderManager resourceLoaderManager) {
		this.resourceLoaderManager = resourceLoaderManager;
	}

	Properties getApplicationProeprties() {
		return applicationProeprties;
	}
	
	String getApplicationProperty(String propertyName) {
		return applicationProeprties.getProperty(propertyName);
	}

	@PostConstruct
	private void loadApplicationProperties() {
		loadProperties(applicationProeprties, "classpath:application.properties");
		overrideApplicationProperties(applicationProeprties, System.getenv(), "System environment");
		overrideApplicationProperties(applicationProeprties, System.getProperties(), "System properties");
		logger.log(Level.INFO, "Application properties have been loaded successfully");
	}

	private void overrideApplicationProperties(Properties applicationProperties, Map<?, ?> externalProperties, String description) {		
		overrideProperties(applicationProperties, externalProperties, description);
		
		Optional<String> configFilePath = findConfigFilePath(externalProperties);
		if (isConfigFileExists(configFilePath)) {
			overridePropertiesFromConfigFile(applicationProperties, configFilePath.get(), description);
		}
	}

	private void overrideProperties(Properties applicationProperties, Map<?, ?> externalProperties,
			String description) {
		for (Map.Entry<?, ?> entry : externalProperties.entrySet()) {
			String key = String.valueOf(entry.getKey());
			if (key.startsWith(APPLICATION_CONFIG_PROPERTY_PREFIX)) {
				applicationProperties.setProperty(key, String.valueOf(entry.getValue()));
				logger.log(Level.INFO, "Application property {0} has been overriden with value defined in the {1}",
						new String[] { key, description });
			}
		}
	}

	private Optional<String> findConfigFilePath(Map<?, ?> map) {
		String configFilePath =  String.valueOf(map.get(APPLICATION_CONFIG_FILE));
		return configFilePath.equals("null") ? Optional.empty() : Optional.of(configFilePath);
	}

	private void overridePropertiesFromConfigFile(Properties applicationProperties, String configFilePath,
			String description) {
		loadProperties(applicationProperties, configFilePath);
		logger.log(Level.INFO, "Application properties have been overriden from file {0}, defined in the {1}",
				new String[] { configFilePath, description });
	}

	private boolean isConfigFileExists(Optional<String> configFilePath) {
		return configFilePath.isPresent() && Files.exists(Paths.get(configFilePath.get()));
	}
}
