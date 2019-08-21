package org.myphotos.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "imageLink")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ImageLinkSOAP {

	private String url;

	public ImageLinkSOAP() {
	}
	
	public ImageLinkSOAP(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
