package org.myphotos.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
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

	private Long id;
	private String uid;
	private String avatarUrl;
	private String email;
	private String firstName;
	private String lastName;
	private String jobTitle;
	private String location;
	private int photoCount;
	private int rating;

	@Id
	@Column(unique = true, nullable = false, updatable = false)
	@SequenceGenerator(name = "PROFILE_ID_GENERATOR", sequenceName = "PROFILE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_ID_GENERATOR")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Size(max = 255)
	@Column(unique = true, nullable = false, length = 255, updatable = false)
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	@NotNull
	@Size(max = 255)
	@Column(name = "avatar_url", nullable = false, length = 255)
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@NotNull
	@Email
	@Size(max = 100)
	@Column(unique = true, nullable = false, length = 100, updatable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@Size(min = 1, max = 60)
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	@Column(name = "first_name", nullable = false, length = 60)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@NotNull
	@Size(min = 1, max = 60)
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	@Column(name = "last_name", nullable = false, length = 60)
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Transient
	public String getFullName() {
		return firstName + " " + lastName;
	}

	@NotNull
	@Size(min = 5, max = 100)
	@EnglishLanguage(withSpecSymbols = false)
	@Column(name = "job_title", nullable = false, length = 100)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@NotNull
	@Size(min = 5, max = 100)
	@EnglishLanguage(withSpecSymbols = false)
	@Column(nullable = false, length = 100)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Min(0)
	@Column(name = "photo_count", nullable = false)
	public int getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

	@Min(0)
	@Column(nullable = false)
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
