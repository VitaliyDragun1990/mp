package org.myphotos.infra.exception.business;

/**
 * Signals that some business-related object can not be found.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ObjectNotFoundException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message) {
		super(message);
	}

}
