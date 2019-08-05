/**
 * 
 */
package org.myphotos.web.exception;

import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Signals about error in web environment.
 * 
 * @author Vitaliy Dragun
 *
 */
public class HttpStatusException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	private final int status;

	public HttpStatusException(int status, String message) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
