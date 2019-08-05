package org.myphotos.web.security.exception;

import org.myphotos.security.exception.SecurityException;

/**
 * Signals about errors during authentication process.
 * 
 * @author Vitaliy Dragun
 *
 */
public class AuthenticationException extends SecurityException {
	private static final long serialVersionUID = 1L;

	public AuthenticationException(String message) {
		super(message);
	}

}
