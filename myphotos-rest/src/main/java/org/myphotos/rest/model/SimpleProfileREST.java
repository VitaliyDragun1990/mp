package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("SimpleProfile")
public class SimpleProfileREST {

	private Long id;
	private String uid;

	@ApiModelProperty(required = true, value = "Profile id. Used as unique identifier to distinguish profiles via REST API")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ApiModelProperty(
			required = true,
			value = "Profile uid. Can be useflul if user wants to search for particular profile."
					+ "For examle via browser profile unique url will be http://myphotos.com/${uid}"
			)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
