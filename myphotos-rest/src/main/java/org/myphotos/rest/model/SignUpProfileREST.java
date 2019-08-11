package org.myphotos.rest.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlType;

import org.myphotos.domain.entity.Profile;
import org.myphotos.validation.EnglishLanguage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("SignUpProfile")
public class SignUpProfileREST {
	
	private String firstName;
	private String lastName;
	private String jobTitle;
	private String location;

	@NotNull(message = "{Profile.firstName.NotNull}")
	@Size(min = 1, message = "{Profile.firstName.Size}")
	@EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecSymbols = false)
	@ApiModelProperty(required = true, value = "Min size = 1, Only latin characters allowed")
	public String getFirstName() {
		return firstName;
	}
	
	@NotNull(message = "{Profile.lastName.NotNull}")
	@Size(min = 1, message = "{Profile.lastName.Size}")
	@EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecSymbols = false)
	@ApiModelProperty(required = true, value = "Min size = 1, Only latin characters allowed")
	public String getLastName() {
		return lastName;
	}

	@NotNull(message = "{Profile.jobTitle.NotNull}")
	@Size(min = 5, message = "{Profile.jobTitle.Size}")
	@EnglishLanguage(withSpecSymbols = false)
	@ApiModelProperty(required = true, value = "Min size = 15, Only latin characters allowed")
	public String getJobTitle() {
		return jobTitle;
	}

	@NotNull(message = "{Profile.location.NotNull}")
	@Size(min = 5, message = "{Profile.location.Size}")
	@EnglishLanguage(withSpecSymbols = false)
	@ApiModelProperty(required = true, value = "Min size = 5, Only latin characters allowed")
	public String getLocation() {
		return location;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void copyToProfile(Profile profile) {
		profile.setFirstName(getFirstName());
		profile.setLastName(getLastName());
		profile.setJobTitle(getJobTitle());
		profile.setLocation(getLocation());
	}

}
