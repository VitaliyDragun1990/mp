package org.myphotos.infra.exception.business;

import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Signals about business rules violation
 * 
 * @author Vitaliy Dragun
 *
 */
public abstract class BusinessException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message, null, true, false);
	}

}
