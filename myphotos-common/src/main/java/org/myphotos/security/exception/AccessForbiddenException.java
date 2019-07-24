package org.myphotos.security.exception;

/**
 * Signals about access rules violation
 * 
 * @author Vitaliy Dragun
 *
 */
public class AccessForbiddenException extends SecurityException {
	private static final long serialVersionUID = 1L;

	public AccessForbiddenException(String message) {
		super(message);
	}

}
