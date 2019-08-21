package org.myphotos.converter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.myphotos.infra.exception.ConfigurationException;
import org.myphotos.infra.util.Checks;

/**
 * Default implementation that uses Java Reflect API to provide conversion
 * 
 * @author Vitaliy Dragun
 *
 */
@ApplicationScoped
class DefaultModelConverter implements ModelConverter {

	@Inject
	private AbsoluteUrlConverter urlConverter;

	@Override
	public <S, D> D convert(S source, Class<D> destinationClass) {
		checkParams(source, destinationClass);

		try {
			return processConversion(source, destinationClass);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new ConfigurationException(String.format("Can't convert object from %s to %s: %s", source.getClass(),
					destinationClass, e.getMessage()), e);
		}
	}

	@Override
	public <S, D> List<D> convertList(List<S> source, Class<D> destinationClass) {
		List<D> result = new ArrayList<>();

		for (S item : source) {
			result.add(convert(item, destinationClass));
		}

		return result;
	}
	
	private <S, D> D processConversion(S source, Class<D> destinationClass)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		D result = destinationClass.newInstance();
		copyProperties(result, source);
		return result;
	}

	private <S, D> void copyProperties(D result, S source)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PropertyUtilsBean propertyUtils = BeanUtilsBean.getInstance().getPropertyUtils();
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(result);

		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			if (isPropertyConvertable(result, source, propertyUtils, propertyName)) {
				tryToConvertProperty(result, source, propertyUtils, propertyName);
			}
		}
	}

	private <S, D> boolean isPropertyConvertable(D result, S source, PropertyUtilsBean propertyUtils,
			String propertyName) {
		return !"class".equals(propertyName) && propertyUtils.isReadable(source, propertyName)
				&& PropertyUtils.isWriteable(result, propertyName);
	}

	private <D, S> void tryToConvertProperty(D result, S source, PropertyUtilsBean propertyUtils, String propertyName)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object propertyValue = propertyUtils.getProperty(source, propertyName);
		if (propertyValue != null) {
			Object convertedValue = convertValue(propertyName, propertyValue, result, propertyUtils);
			propertyUtils.setProperty(result, propertyName, convertedValue);
		}
	}

	private <D> Object convertValue(String propertyName, Object propertyValue, D result,
			PropertyUtilsBean propertyUtils) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Class<?> resultPropertyClass = propertyUtils.getPropertyType(result, propertyName);
		if (resultPropertyClass.isPrimitive() || propertyValue.getClass().isPrimitive()) {
			return propertyValue;
		} else if (ConverterUtils.isAnnotationPresent(ConvertAsAbsoluteURL.class, result, propertyName)) {
			return urlConverter.convert(String.valueOf(propertyValue));
		} else if (propertyValue.getClass() != resultPropertyClass) {
			return convert(propertyValue, resultPropertyClass);
		}

		return propertyValue;
	}

	private static void checkParams(Object source, Class<?> destinationClass) {
		Checks.checkParam(source != null, "Source to convert from can not be null");
		Checks.checkParam(destinationClass != null, "Destination class to convert to can not be null");
	}

	void setUrlConverter(AbsoluteUrlConverter urlConverter) {
		this.urlConverter = urlConverter;
	}

}
