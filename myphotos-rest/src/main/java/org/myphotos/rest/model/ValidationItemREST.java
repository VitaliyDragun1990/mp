package org.myphotos.rest.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ValidationItem")
public class ValidationItemREST {

	@ApiModelProperty(required = true, value = "Property name")
	private String field;

	@ApiModelProperty(required = true, value = "List of error messages for particular property")
	private List<String> messages;

	public ValidationItemREST() {
	}

	public ValidationItemREST(String field, List<String> messages) {
		this.field = field;
		this.messages = messages;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
