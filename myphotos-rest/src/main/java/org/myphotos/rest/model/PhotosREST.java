package org.myphotos.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("Photos")
public class PhotosREST {

	private List<? extends ProfilePhotoREST> photos;
	private Long total;

	public PhotosREST() {
	}

	public PhotosREST(List<? extends ProfilePhotoREST> photos) {
		this.photos = photos;
	}

	@ApiModelProperty(required = true, value = "Photo list")
	public List<? extends ProfilePhotoREST> getPhotos() {
		return photos;
	}

	public void setPhotos(List<? extends ProfilePhotoREST> photos) {
		this.photos = photos;
	}

	@ApiModelProperty(required = false, value = "Total photo count")
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
