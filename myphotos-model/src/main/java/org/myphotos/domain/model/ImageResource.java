package org.myphotos.domain.model;

import java.nio.file.Path;

/**
 * Represents abstract image resource
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageResource extends AutoCloseable {

	/**
	 * Returns path image resource
	 */
	Path getPath();
}
