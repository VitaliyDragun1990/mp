package org.myphotos.infra.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * Contains general purpose utility functions
 * 
 * @author Vitaly Dragun
 *
 */
public class CommonUtils {

	/**
	 * Returns not-null unmodifiable copy of the source set
	 */
	public static <T> Set<T> getSafeSet(Set<T> source) {
		return Collections.unmodifiableSet(Optional.ofNullable(source).orElse(Collections.emptySet()));
	}

	/**
	 * Returns non-null unmodifiable copy of the source list
	 */
	public static <T> List<T> getSafeList(List<T> source) {
		return Collections.unmodifiableList(Optional.ofNullable(source).orElse(Collections.emptyList()));
	}

	/**
	 * Returns non-null unmodifiable copy of the source map
	 */
	public static <K, V> Map<K, V> getSafeMap(Map<K, V> source) {
		return Collections.unmodifiableMap(Optional.ofNullable(source).orElse(Collections.emptyMap()));
	}

	/**
	 * Returns list of methods annotated with specified annotation or empty list if
	 * no such methods was found
	 * 
	 */
	public static List<Method> getMethodsWithAnnotation(Class<?> cls, Class<? extends Annotation> annotation) {
		return Arrays.asList(MethodUtils.getMethodsWithAnnotation(cls, annotation));
	}

	/**
	 * Generates string representation of the specified object
	 */
	public static String toString(Object param) {
		return ReflectionToStringBuilder.toString(param, ToStringStyle.MULTI_LINE_STYLE);
	}

	public static boolean isBlank(String string) {
		return StringUtils.isBlank(string);
	}

	public static boolean isNotBlank(String string) {
		return !StringUtils.isBlank(string);
	}

	private CommonUtils() {
	}

}
