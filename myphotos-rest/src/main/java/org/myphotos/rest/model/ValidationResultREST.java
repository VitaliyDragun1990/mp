package org.myphotos.rest.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ValidationResult")
public class ValidationResultREST extends ErrorMessageREST {

	@ApiModelProperty(required = true)
	private List<ValidationItemREST> items;

	public ValidationResultREST() {
		super("Validation error", true);
	}

	public ValidationResultREST(List<ValidationItemREST> items) {
		this();
		this.items = items;
	}

	public List<ValidationItemREST> getItems() {
		return items;
	}

	public void setItems(List<ValidationItemREST> items) {
		this.items = items;
	}

}
