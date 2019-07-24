package org.myphotos.social.exception;

import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Signals about social data retrieving problems
 * 
 * @author Vitaliy Dragun
 *
 */
public class SocialDataRetrievingException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public SocialDataRetrievingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SocialDataRetrievingException(String message) {
		super(message);
	}
}
