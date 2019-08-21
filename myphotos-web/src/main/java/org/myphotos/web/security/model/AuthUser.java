package org.myphotos.web.security.model;

import java.io.Serializable;

public class AuthUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final Long id;
	private final String uid;
	private final String email;

	public AuthUser(Long id, String uid, String email) {
		super();
		this.id = id;
		this.uid = uid;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public String getEmail() {
		return email;
	}

}
