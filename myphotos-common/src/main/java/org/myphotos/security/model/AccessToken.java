package org.myphotos.security.model;

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
public class AccessToken extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@Column(unique = true, nullable = false)
	private String token;
	
	@NotNull
	@JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Profile profile;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
