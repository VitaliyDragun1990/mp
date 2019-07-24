package org.myphotos.transformer.impl;

import java.util.List;

import javax.enterprise.inject.Vetoed;

@Vetoed
class BasicFieldProvider implements FieldProvider {

	@Override
	public List<String> getSimilarFieldNames(Class<?> source, Class<?> dest) {
		return ReflectionUtils.findSimilarFields(source, dest);
	}

}
