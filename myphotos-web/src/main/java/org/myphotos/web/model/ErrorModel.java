package org.myphotos.web.model;

/**
 * Represents some kind of error in web environment
 * 
 * @author Vitaliy Dragun
 *
 */
public class ErrorModel {
	private final int status;
	private final String message;

	public ErrorModel(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
}
