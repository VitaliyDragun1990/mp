package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

import org.myphotos.converter.ConvertAsAbsoluteURL;

@XmlType(name = "")
public class ProfileREST extends SimpleProfileREST {

	protected String firstName;

	protected String lastName;

	@ConvertAsAbsoluteURL
	protected String avatarUrl;

	protected String jobTitle;

	protected String location;

	private int photoCount;

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

	public void setLocation(String lcoation) {
		this.location = lcoation;
	}

	public int getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

}
