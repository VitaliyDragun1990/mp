package org.myphotos.web.form;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.myphotos.domain.entity.Profile;
import org.myphotos.validation.EnglishLanguage;
import org.myphotos.validation.group.ProfileUpdateGroup;
import org.myphotos.validation.group.SignUpGroup;

public class ProfileForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String uid;
	
	private String avatarUrl;
	
	private boolean agree;
	

	private String firstName;


	private String lastName;
	

	private String jobTitle;


	private String location;
	
	public ProfileForm() {
	}
	
	public ProfileForm(Profile profile) {
		setUid(profile.getUid());
		setFirstName(profile.getFirstName());
		setLastName(profile.getLastName());
		setJobTitle(profile.getJobTitle());
		setLocation(profile.getLocation());
		setAvatarUrl(profile.getAvatarUrl());
	}
	
	public void copyToProfile(Profile profile) {
		profile.setFirstName(getFirstName());
		profile.setLastName(getLastName());
		profile.setJobTitle(getJobTitle());
		profile.setLocation(getLocation());
	}
	
	@NotNull(message = "{Profile.firstName.NotNull}", groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	@Size(min = 1, max = 60, message = "{Profile.firstName.Size}", groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false, groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@NotNull(message = "{Profile.lastName.NotNull}", groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	@Size(min = 1, max = 60, message = "{Profile.lastName.Size}", groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false, groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@NotNull(message = "{Profile.jobTitle.NotNull}", groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	@Size(min = 5, max = 100, message = "{Profile.jobTitle.Size}", groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	@EnglishLanguage(withSpecSymbols = false, groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	public String getJobTitle() {
		return jobTitle;
	}
	
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	@NotNull(message = "{Profile.location.NotNull}", groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	@Size(min = 5, max = 100, message = "{Profile.location.Size}", groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	@EnglishLanguage(withSpecSymbols = false, groups = {SignUpGroup.class, ProfileUpdateGroup.class})
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@AssertTrue(message = "{ProfileForm.agree.AssertTrue}", groups = SignUpGroup.class)
	public boolean isAgree() {
		return agree;
	}
	
	public void setAgree(boolean agree) {
		this.agree = agree;
	}
	
	public String getFullName() {
		return String.format("%s %s", firstName, lastName);
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
