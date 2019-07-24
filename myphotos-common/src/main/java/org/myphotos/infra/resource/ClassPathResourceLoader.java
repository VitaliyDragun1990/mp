package org.myphotos.infra.resource;

import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;

import org.myphotos.infra.exception.ConfigurationException;

@ApplicationScoped
public class ClassPathResourceLoader implements ResourceLoader {

	@Override
	public boolean isSupport(String resourceName) {
		return resourceName.startsWith("classpath:");
	}

	@Override
	public InputStream getInputStream(String resourceName) {
		String classPathResourceName = resourceName.replace("classpath:", "");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			InputStream inputStream = classLoader.getResourceAsStream(classPathResourceName);
			if (inputStream != null) {
				return inputStream;
			}
		}
		throw new ConfigurationException("Classpath resource not found: " + classPathResourceName);
	}

}
