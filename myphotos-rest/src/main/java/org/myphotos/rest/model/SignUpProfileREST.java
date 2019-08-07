package org.myphotos.rest.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlType;

import org.myphotos.domain.entity.Profile;
import org.myphotos.validation.EnglishLanguage;

@XmlType(name = "")
public class SignUpProfileREST extends AuthenticationCodeREST { // TODO: incorrect use of inheritance

	private String firstName;
	private String lastName;
	private String jobTitle;
	private String location;

	@NotNull(message = "{Profile.firstName.NotNull}")
	@Size(min = 1, message = "{Profile.firstName.Size}")
	@EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecSymbols = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@NotNull(message = "{Profile.lastName.NotNull}")
	@Size(min = 1, message = "{Profile.lastName.Size}")
	@EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecSymbols = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@NotNull(message = "{Profile.jobTitle.NotNull}")
	@Size(min = 5, message = "{Profile.jobTitle.Size}")
	@EnglishLanguage(withSpecSymbols = false)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@NotNull(message = "{Profile.location.NotNull}")
	@Size(min = 5, message = "{Profile.location.Size}")
	@EnglishLanguage(withSpecSymbols = false)
	public String getLocation() {
		return location;
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
