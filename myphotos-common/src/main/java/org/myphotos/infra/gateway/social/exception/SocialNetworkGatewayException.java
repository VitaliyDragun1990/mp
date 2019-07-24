package org.myphotos.infra.gateway.social.exception;

import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Signals about error during comminication with social network via gateway
 * 
 * @author Vitaliy Dragun
 *
 */
public class SocialNetworkGatewayException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public SocialNetworkGatewayException(String message, Throwable cause) {
		super(message, cause);
	}

}
