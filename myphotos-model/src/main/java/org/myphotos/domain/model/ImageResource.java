package org.myphotos.domain.model;

import java.nio.file.Path;

/**
 * Represents temporary stored image resource
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageResource extends AutoCloseable {

	/**
	 * Returns path to temporary stored image resource
	 */
	Path getTemporaryPath();
}
