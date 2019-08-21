package org.myphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ErrorMessage")
public class ErrorMessageREST {

	private String message;
	private boolean userError;
	
	public ErrorMessageREST() {
	}
	
	public ErrorMessageREST(String message) {
		this(message, false);
	}

	public ErrorMessageREST(String message, boolean userError) {
		this.message = message;
		this.userError = userError;
	}

	@ApiModelProperty(
			required = true,
			value = "Error message. Should be displayed to the user if userError=true,"
					+ " otherwise it is message for the developer"
			)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@ApiModelProperty(
			required = true,
			value = "Category of the error message. If userError=true, message should be displayed to the user,"
					+ " otherwise it is message for the developer"
			)
	public boolean isUserError() {
		return userError;
	}

	public void setUserError(boolean userError) {
		this.userError = userError;
	}
	
}
