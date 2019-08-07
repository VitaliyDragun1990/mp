package org.myphotos.converter;

/**
 * Responsible for converting relative URL into absolute one.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface AbsoluteUrlConverter {

	String convert(String relativeUrl);
}
