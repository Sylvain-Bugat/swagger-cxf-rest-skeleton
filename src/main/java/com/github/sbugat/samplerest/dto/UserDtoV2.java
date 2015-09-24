package com.github.sbugat.samplerest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;

@XmlRootElement(
		name = "User")
public class UserDtoV2 extends UserDto {

	private int userStatus;

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
