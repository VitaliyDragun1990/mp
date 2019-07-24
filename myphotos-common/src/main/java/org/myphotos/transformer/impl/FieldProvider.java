package org.myphotos.transformer.impl;

import java.util.List;

import javax.enterprise.inject.Vetoed;

/**
 * Functionality of field preparation
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
interface FieldProvider {

	/**
	 * 
	 * Returns list of similar field names for source/destination classes
	 */
	List<String> getSimilarFieldNames(Class<?> source, Class<?> dest);
}
