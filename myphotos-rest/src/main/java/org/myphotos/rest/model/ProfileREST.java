package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

import org.myphotos.converter.ConvertAsAbsoluteURL;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("Profile")
public class ProfileREST extends SimpleProfileREST {

	@ApiModelProperty(required = true)
	protected String firstName;

	@ApiModelProperty(required = true)
	protected String lastName;

	@ConvertAsAbsoluteURL
	@ApiModelProperty(required = true)
	protected String avatarUrl;

	@ApiModelProperty(required = true)
	protected String jobTitle;

	@ApiModelProperty(required = true)
	protected String location;

	@ApiModelProperty(required = true)
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
