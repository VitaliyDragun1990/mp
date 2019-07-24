package org.myphotos.infra.gateway.social.model;

import javax.enterprise.inject.Vetoed;

/**
 * Represents abstraction over user's account from social network
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public class SocialNetworkAccount {

	private final String firstName;
	private final String lastName;
	private final String email;
	private final String avatarUrl;

	public SocialNetworkAccount(String firstName, String lastName, String email, String avatarUrl) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

}
