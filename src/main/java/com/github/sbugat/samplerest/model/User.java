/**
 *  Copyright 2015 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.sbugat.samplerest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;

@Entity
@XmlRootElement(
		name = "User")
public class User {

	@Id
	@GeneratedValue(
			strategy = GenerationType.AUTO)
	private Long id;

	@Column(
			unique = true)
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	private String roles;

	private int userStatus;

	@XmlElement(
			name = "id")
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

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