package org.myphotos.infra.producer;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.myphotos.infra.cdi.qualifier.Property;
import org.myphotos.infra.exception.ConfigurationException;

@Dependent
public class PropertyProducer {

	@Inject
	private ApplicationPropertiesStorage applicationPropertiesStorage;

	@Produces
	@Property
	public String resolveStringPropertyValue(InjectionPoint injectionPoint) {
		return resolvePropertyValue(injectionPoint);
	}

	@Produces
	@Property
	public int resilveIntPropertyValue(InjectionPoint injectionPoint) {
		return Integer.parseInt(resolvePropertyValue(injectionPoint));
	}

	@Produces
	@Property
	public boolean resolveBooleanPropertyValue(InjectionPoint injectionPoint) {
		return Boolean.parseBoolean(resolvePropertyValue(injectionPoint));
	}

	private String resolvePropertyValue(InjectionPoint injectionPoint) {
		String className = injectionPoint.getMember().getDeclaringClass().getName();
		String memberName = injectionPoint.getMember().getName();
		Property property = injectionPoint.getAnnotated().getAnnotation(Property.class);
		return resolvePropertyValue(className, memberName, property);
	}

	private String resolvePropertyValue(String className, String memberName, Property property) {
		String propertyName = getPropertyName(className, memberName, property);
		String propertyValue = applicationPropertiesStorage.getApplicationProperty(propertyName);
		if (propertyValue == null) {
			throw new ConfigurationException(String.format("Can't resolve property: '%s' for injection to %s.%s",
					propertyName, className, memberName));
		} else {
			return propertyValue;
		}
	}

	private String getPropertyName(String className, String memberName, Property property) {
		String value = property.value();
		if ("".equals(value)) {
			return String.format("%s.%s", className, memberName);
		} else {
			return value;
		}
	}
}
