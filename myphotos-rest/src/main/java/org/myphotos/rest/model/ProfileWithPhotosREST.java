package org.myphotos.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class ProfileWithPhotosREST extends ProfileREST {

	private List<? extends ProfilePhotoREST> photos;

	public List<? extends ProfilePhotoREST> getPhotos() {
		return photos;
	}

	public void setPhotos(List<? extends ProfilePhotoREST> photos) {
		this.photos = photos;
	}

}
