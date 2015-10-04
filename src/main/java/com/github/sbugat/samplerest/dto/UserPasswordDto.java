package com.github.sbugat.samplerest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
		name = "UserPassword")
public class UserPasswordDto {

	private String username;
	private String password;

	@XmlElement(
			name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@XmlElement(
			name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
