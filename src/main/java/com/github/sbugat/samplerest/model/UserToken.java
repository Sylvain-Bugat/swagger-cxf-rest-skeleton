package com.github.sbugat.samplerest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserToken {

	@Id
	@GeneratedValue(
			strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	@Column(
			unique = true)
	private String authenticationToken;

	private int tokenStatus;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

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