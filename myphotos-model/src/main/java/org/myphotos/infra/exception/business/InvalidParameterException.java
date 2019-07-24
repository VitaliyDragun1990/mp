package org.myphotos.infra.exception.business;

/**
 * Signals that incorrect parameter was passed to method/constructor
 * 
 * @author Vitaliy Dragun
 *
 */
public class InvalidParameterException extends InvalidWorkFlowException {
	private static final long serialVersionUID = 1L;

	public InvalidParameterException(String message) {
		super(message);
	}

}
