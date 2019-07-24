package org.myphotos.infra.exception;

import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Signals about errors concerning application configuration
 * 
 * @author Vitaliy Dragun
 *
 */
public class ConfigurationException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
