package com.github.sbugat.samplerest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;

@XmlRootElement(
		name = "User")
public class UserDto {

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	private String roles;

	private int userStatus;

	@XmlElement(
			name = "firstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	@XmlElement(
			name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@XmlElement(
			name = "lastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	@XmlElement(
			name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@XmlElement(
			name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@XmlElement(
			name = "roles")
	public String getRoles() {
		return roles;
	}

	public void setRoles(final String roles) {
		this.roles = roles;
	}

	@XmlElement(
			name = "userStatus")
	@ApiModelProperty(
			value = "User Status",
			allowableValues = "1-registered,2-active,3-blocked,4-closed")
	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(final int userStatus) {
		this.userStatus = userStatus;
	}
}
