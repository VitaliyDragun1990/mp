package org.myphotos.transformer.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;

@Dependent
class CachedFieldProvider implements FieldProvider {

	/**
	 * Mapping between transformation pair(concatenated class names) and field list
	 */
	private final Map<String, List<String>> cache;
	
	public CachedFieldProvider() {
		this.cache = new HashMap<>();
	}

	@Override
	public List<String> getSimilarFieldNames(Class<?> source, Class<?> dest) {
		String key = source.getSimpleName() + dest.getSimpleName();
		return cache.computeIfAbsent(key, k -> ReflectionUtils.findSimilarFields(source, dest));
	}

}
