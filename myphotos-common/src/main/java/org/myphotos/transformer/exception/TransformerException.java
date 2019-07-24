package org.myphotos.transformer.exception;

import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Signals about error during transformation process
 * 
 * @author Vitaliy Dragun
 *
 */
public class TransformerException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public TransformerException(String message) {
		super(message);
	}

	public TransformerException(String message, Throwable cause) {
		super(message, cause);
	}

}
