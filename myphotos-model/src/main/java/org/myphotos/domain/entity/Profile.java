package org.myphotos.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.myphotos.validation.Email;
import org.myphotos.validation.EnglishLanguage;

/**
 * This component is used both as domain model and entity
 * @author Vitaliy Dragun
 *
 */
@Entity
@Table(name = "profile", catalog = "myphotos", schema = "public",
	uniqueConstraints = { 
			@UniqueConstraint(columnNames = { "email" }),
			@UniqueConstraint(columnNames = { "uid" }) }
)
public class Profile extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, updatable = false)
	@SequenceGenerator(name = "PROFILE_ID_GENERATOR", sequenceName = "PROFILE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_ID_GENERATOR")
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(unique = true, nullable = false, length = 255, updatable = false)
	private String uid;

	@NotNull
	@Size(max = 255)
	@Column(name = "avatar_url", nullable = false, length = 255)
	private String avatarUrl;

	@NotNull
	@Email
	@Size(max = 100)
	@Column(unique = true, nullable = false, length = 100, updatable = false)
	private String email;

	@NotNull(message = "{Profile.firstName.NotNull}")
	@Size(min = 1, max = 60, message = "{Profile.firstName.Size}")
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	@Column(name = "first_name", nullable = false, length = 60)
	private String firstName;

	@NotNull(message = "{Profile.lastName.NotNull}")
	@Size(min = 1, max = 60, message = "{Profile.lastName.Size}")
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	@Column(name = "last_name", nullable = false, length = 60)
	private String lastName;
	
	@NotNull(message = "{Profile.jobTitle.NotNull}")
	@Size(min = 5, max = 100, message = "{Profile.jobTitle.Size}")
	@EnglishLanguage(withSpecSymbols = false)
	@Column(name = "job_title", nullable = false, length = 100)
	private String jobTitle;

	@NotNull(message = "{Profile.location.NotNull}")
	@Size(min = 5, max = 100, message = "{Profile.location.Size}")
	@EnglishLanguage(withSpecSymbols = false)
	@Column(nullable = false, length = 100)
	private String location;

	@Min(0)
	@Column(name = "photo_count", nullable = false)
	private int photoCount;

	@Min(0)
	@Column(nullable = false)
	private int rating;

	public Long getId() {
		return id;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
