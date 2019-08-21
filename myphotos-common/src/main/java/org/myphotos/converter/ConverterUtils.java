package org.myphotos.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.enterprise.inject.Vetoed;

@Vetoed
class ConverterUtils {

	/**
	 * Checks whether a specified annotation is
	 * present on some property on some bean.
	 * 
	 * @param annotationClass class of the annotation to check for
	 * @param bean object to check
	 * @param propertyName name of the property to check for the annotation presence
	 * @return
	 */
	public static boolean isAnnotationPresent(Class<? extends Annotation> annotationClass, Object bean,
			String propertyName) {
		return isAnnotationPresentOnGetter(annotationClass, bean, propertyName)
				||
				isAnnotationPresentOnField(annotationClass, bean, propertyName);
	}
	
	private static boolean isAnnotationPresentOnGetter(Class<? extends Annotation> annotationClass, Object bean,
			String propertyName) {
		Class<?> beanClass = bean.getClass();
		String[] getterNames = getGetterMethodNames(propertyName);
		
		while (beanClass != null) {
			for (String getterName : getterNames) {
				try {
					Method getter = beanClass.getDeclaredMethod(getterName);
					if (getter.isAnnotationPresent(annotationClass)) {
						return true;
					}
				} catch (NoSuchMethodException e) {
					// do nothing
				}
			}
			beanClass = beanClass.getSuperclass();
		}
		
		return false;
	}
	
	private static boolean isAnnotationPresentOnField(Class<? extends Annotation> annotationClass, Object bean,
			String propertyName) {
		Class<?> beanClass = bean.getClass();
		
		while (beanClass != null) {
			try {
				Field field = beanClass.getDeclaredField(propertyName);
				if (field.isAnnotationPresent(annotationClass)) {
					return true;
				}
			} catch (NoSuchFieldException e) {
				// do nothing
			}
			beanClass = beanClass.getSuperclass();
		}
		
		return false;
	}

	private static String[] getGetterMethodNames(String propertyName) {
		if (propertyName.length() > 1) {
			return new String[] {
					"get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1),
					"is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1)
			};
		} else {
			return new String[] {
					"get" + Character.toUpperCase(propertyName.charAt(0)),
					"is" + Character.toUpperCase(propertyName.charAt(0))
			};
		}
	}
	
	private ConverterUtils() {}
}
