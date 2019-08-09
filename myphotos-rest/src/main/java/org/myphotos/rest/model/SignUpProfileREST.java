package org.myphotos.rest.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlType;

import org.myphotos.domain.entity.Profile;
import org.myphotos.validation.EnglishLanguage;

@XmlType(name = "")
public class SignUpProfileREST extends ProfileREST {

	@NotNull(message = "{Profile.firstName.NotNull}")
	@Size(min = 1, message = "{Profile.firstName.Size}")
	@EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecSymbols = false)
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@NotNull(message = "{Profile.lastName.NotNull}")
	@Size(min = 1, message = "{Profile.lastName.Size}")
	@EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecSymbols = false)
	@Override
	public String getLastName() {
		return lastName;
	}

	@NotNull(message = "{Profile.jobTitle.NotNull}")
	@Size(min = 5, message = "{Profile.jobTitle.Size}")
	@EnglishLanguage(withSpecSymbols = false)
	@Override
	public String getJobTitle() {
		return jobTitle;
	}

	@NotNull(message = "{Profile.location.NotNull}")
	@Size(min = 5, message = "{Profile.location.Size}")
	@EnglishLanguage(withSpecSymbols = false)
	@Override
	public String getLocation() {
		return location;
	}
	
	public void copyToProfile(Profile profile) {
		profile.setFirstName(getFirstName());
		profile.setLastName(getLastName());
		profile.setJobTitle(getJobTitle());
		profile.setLocation(getLocation());
	}

}
