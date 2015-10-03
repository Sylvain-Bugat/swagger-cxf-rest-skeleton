package com.github.sbugat.samplerest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserToken extends GenericEntity {

	@ManyToOne
	private User user;

	@Column(
			unique = true)
	private String authenticationToken;

	private int tokenStatus;

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(final String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public int getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(final int tokenStatus) {
		this.tokenStatus = tokenStatus;
	}
}