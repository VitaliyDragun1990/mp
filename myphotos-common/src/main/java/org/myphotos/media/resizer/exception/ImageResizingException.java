package org.myphotos.media.resizer.exception;

import javax.enterprise.inject.Vetoed;

import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Signals about error during image resizing process
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public class ImageResizingException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public ImageResizingException(String message, Throwable cause) {
		super(message, cause);
	}

}
