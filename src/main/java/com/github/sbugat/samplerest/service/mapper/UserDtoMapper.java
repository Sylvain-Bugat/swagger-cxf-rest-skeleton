package com.github.sbugat.samplerest.service.mapper;

import javax.inject.Named;

import com.github.sbugat.samplerest.dto.UserDto;
import com.github.sbugat.samplerest.model.User;

import ma.glasnost.orika.CustomMapper;

@Named
public class UserDtoMapper extends CustomMapper<User, UserDto> {
	// Nothing to customize
}
