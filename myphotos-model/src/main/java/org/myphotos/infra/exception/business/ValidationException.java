package org.myphotos.infra.exception.business;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * Raised when attribute values of the object model violates business rules or
 * restrictions.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ValidationException extends BusinessException {
	private static final long serialVersionUID = 1L;
	
	private final transient Set<ConstraintViolation<?>> constraints;

	public ValidationException(String message) {
		super(message);
		this.constraints = Collections.emptySet();
	}

	public ValidationException(String message, Set<ConstraintViolation<?>> constraints) {
		super(message);
		this.constraints = constraints;
	}

	public Set<ConstraintViolation<?>> getConstraints() {
		return constraints;
	}
	
}
