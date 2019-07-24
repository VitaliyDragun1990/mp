package org.myphotos.security.exception;

import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Base parent class for all security-related exceptions
 * 
 * @author Vitaliy Dragun
 *
 */
public abstract class SecurityException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public SecurityException(String message) {
		super(message, null, true, false);
	}

}
