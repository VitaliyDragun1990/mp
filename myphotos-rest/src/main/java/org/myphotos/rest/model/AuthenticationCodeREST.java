package org.myphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("AuthenticationCode")
public class AuthenticationCodeREST {

	private String code;

	@ApiModelProperty(required = true, value = "Authentication code retrieved from social service")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
