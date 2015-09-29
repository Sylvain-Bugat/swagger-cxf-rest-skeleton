package com.github.sbugat.samplerest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class User {

	@Id
	@GeneratedValue(
			generator = "system-uuid")
	@GenericGenerator(
			name = "system-uuid",
			strategy = "uuid")
	@Column(
			length = 32,
			updatable = false,
			nullable = false)
	private String uuid;

	@Column(
			unique = true)
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	private String roles;

	private int userStatus;

	public String getUuid() {
		return uuid;
	}

	public void setId(final String uuid) {
		this.uuid = uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(final String roles) {
		this.roles = roles;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(final int userStatus) {
		this.userStatus = userStatus;
	}
}