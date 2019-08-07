package org.myphotos.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class PhotosREST {

	private List<? extends ProfilePhotoREST> photos;

	private Long total;

	public PhotosREST() {
	}

	public PhotosREST(List<? extends ProfilePhotoREST> photos) {
		this.photos = photos;
	}

	public List<? extends ProfilePhotoREST> getPhotos() {
		return photos;
	}

	public void setPhotos(List<? extends ProfilePhotoREST> photos) {
		this.photos = photos;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
