package org.myphotos.security.exception;

/**
 * Signals about errors during sign-up process
 * 
 * @author Vitaliy Dragun
 *
 */
public class SignUpProcessException extends SecurityException {
	private static final long serialVersionUID = 1L;

	public SignUpProcessException(String message) {
		super(message);
	}

}
