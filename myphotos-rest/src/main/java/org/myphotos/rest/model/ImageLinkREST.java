package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("ImageLink")
public class ImageLinkREST {

	@ApiModelProperty(required = true)
	private String url;

	public ImageLinkREST() {
	}

	public ImageLinkREST(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
