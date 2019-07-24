package org.myphotos.infra.exception.business;

/**
 * Signals about error in the application workflow (e.g. component protocol violation, etc.)
 * @author Vitaliy Dragun
 *
 */
public class InvalidWorkFlowException extends BusinessException {
	private static final long serialVersionUID = 8590528229448366346L;

	public InvalidWorkFlowException(String message) {
		super(message);
	}

}
