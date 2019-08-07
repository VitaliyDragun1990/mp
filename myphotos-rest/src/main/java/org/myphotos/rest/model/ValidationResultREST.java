package org.myphotos.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class ValidationResultREST extends ErrorMessageREST {

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
