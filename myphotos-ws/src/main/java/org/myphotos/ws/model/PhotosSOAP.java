package org.myphotos.ws.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "photos")
@XmlAccessorType(XmlAccessType.FIELD)
public class PhotosSOAP {

	@XmlElement(name = "photo")
	private List<PhotoSOAP> photos;

	private Long total;

	public List<PhotoSOAP> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoSOAP> photos) {
		this.photos = photos;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
