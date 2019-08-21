package org.myphotos.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("ProfileWithPhotos")
public class ProfileWithPhotosREST extends ProfileREST {

	private List<? extends ProfilePhotoREST> photos;

	@ApiModelProperty(required = true, value = "Photo list")
	public List<? extends ProfilePhotoREST> getPhotos() {
		return photos;
	}

	public void setPhotos(List<? extends ProfilePhotoREST> photos) {
		this.photos = photos;
	}

}
