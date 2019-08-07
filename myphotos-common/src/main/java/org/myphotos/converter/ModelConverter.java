package org.myphotos.converter;

import java.util.List;

/**
 * Responsible for converting instances of one type (source type) into another
 * type (destination/model type)
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ModelConverter {

	<S, D> D convert(S source, Class<D> destinationClass);
	
	<S, D> List<D> convertList(List<S> source, Class<D> destinationClass);
}
