package org.myphotos.media.storage.exception;

import javax.enterprise.inject.Vetoed;

import org.myphotos.infra.exception.base.ApplicationException;
import org.myphotos.media.storage.ImageStorage;

/**
 * Signals about an error in the {@link ImageStorage} workflow
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public class ImageStorageException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public ImageStorageException(String message, Throwable cause) {
		super(message, cause);
	}

}
