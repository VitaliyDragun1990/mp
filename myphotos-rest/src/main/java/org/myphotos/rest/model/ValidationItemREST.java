package org.myphotos.rest.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ValidationItem")
public class ValidationItemREST {

	private String field;
	private List<String> messages;

	public ValidationItemREST() {
	}

	public ValidationItemREST(String field, List<String> messages) {
		this.field = field;
		this.messages = messages;
	}

	@ApiModelProperty(required = true, value = "Property name")
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@ApiModelProperty(required = true, value = "List of error messages for particular property")
	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
