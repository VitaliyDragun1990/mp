package org.myphotos.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "photo", catalog = "myphotos", schema = "public")
public class Photo extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String smallUrl;
	private String largeUrl;
	private String originalUrl;
	private long downloads;
	private long views;
	private Profile profile;
	
	@Id
	@Column(unique = true, nullable = false, updatable = false)
	@SequenceGenerator(name="PHOTO_ID_GENERATOR", sequenceName="PHOTO_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PHOTO_ID_GENERATOR")
	public Long getId() {
		return id;
	}

	@NotNull
	@Size(max = 255)
	@Column(name="small_url", nullable = false, length = 255, updatable = false)
	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	@NotNull
	@Size(max = 255)
	@Column(name="large_url", nullable = false, length = 255, updatable = false)
	public String getLargeUrl() {
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}

	@NotNull
	@Size(max = 255)
	@Column(name="original_url", nullable = false, length = 255, updatable = false)
	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	@Min(0)
	@Column(nullable = false)
	public long getDownloads() {
		return downloads;
	}

	public void setDownloads(long downloads) {
		this.downloads = downloads;
	}

	@Min(0)
	@Column(nullable = false)
	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	@NotNull
	@JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false, updatable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
