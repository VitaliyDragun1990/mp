package org.myphotos.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.myphotos.converter.ConvertAsAbsoluteURL;

@XmlRootElement(name = "profilePhoto")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProfilePhotoSOAP {

	private Long id;
	private String smallUrl;
	private long views;
	private long downloads;

	@XmlAttribute(required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ConvertAsAbsoluteURL
	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	public long getDownloads() {
		return downloads;
	}

	public void setDownloads(long downloads) {
		this.downloads = downloads;
	}
	
}
