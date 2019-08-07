package org.myphotos.ws.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "profilePhotos")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProfilePhotosSOAP {

	@XmlElement(name = "photo")
	private List<ProfilePhotoSOAP> photos;

	private Long total;

	public List<ProfilePhotoSOAP> getPhotos() {
		return photos;
	}

	public void setPhotos(List<ProfilePhotoSOAP> photos) {
		this.photos = photos;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
