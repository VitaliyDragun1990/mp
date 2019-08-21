package org.myphotos.ws.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.myphotos.converter.ConvertAsAbsoluteURL;

@XmlRootElement(name = "profile")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProfileSOAP {

	private Long id;
	private String uid;
	private String firstName;
	private String lastName;
	private String avatarUrl;
	private String jobTitle;
	private String location;
	private int photoCount;
	private List<ProfilePhotoSOAP> photos;

	@XmlAttribute(required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@ConvertAsAbsoluteURL
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

	@XmlElementWrapper(name = "photos")
	@XmlElement(name = "photo")
	public List<ProfilePhotoSOAP> getPhotos() {
		return photos;
	}

	public void setPhotos(List<ProfilePhotoSOAP> photos) {
		this.photos = photos;
	}

}
