package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("UploadPhotoResult")
public class UploadPhotoResultREST extends ImageLinkREST {

	private Long id;

	public UploadPhotoResultREST(Long id, String url) {
		super(url);
		this.id = id;
	}

	public UploadPhotoResultREST() {
	}

	@ApiModelProperty(required = true, value = "Id of the uploaded photo")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
