package org.myphotos.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.myphotos.domain.entity.AbstractEntity;
import org.myphotos.domain.entity.Profile;

@Entity
@Table(name="access_token", catalog = "myphotos", schema = "public")
public class AccessToken extends AbstractEntity<String> {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Id
	@Column(name = "token", unique = true, nullable = false)
	private String id;
	
	@NotNull
	@JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Profile profile;

	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getToken() {
		return getId();
	}
	
	public void setToken(String token) {
		setId(token);
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
