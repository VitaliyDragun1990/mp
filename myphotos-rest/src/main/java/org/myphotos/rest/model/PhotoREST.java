package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("Photo")
public class PhotoREST extends ProfilePhotoREST {

	private ProfileREST profile;

	@ApiModelProperty(required = true)
	public ProfileREST getProfile() {
		return profile;
	}

	public void setProfile(ProfileREST profile) {
		this.profile = profile;
	}

}
