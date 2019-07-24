package org.myphotos.security.exception;

/**
 * Signals about access attempt using invalid access token
 * 
 * @author Vitaliy Dragun
 *
 */
public class InvalidAccessTokenException extends SecurityException {
	private static final long serialVersionUID = 1L;

	public InvalidAccessTokenException(String message) {
		super(message);
	}

}
