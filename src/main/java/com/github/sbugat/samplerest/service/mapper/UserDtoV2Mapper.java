package com.github.sbugat.samplerest.service.mapper;

import javax.inject.Named;

import com.github.sbugat.samplerest.dto.UserDtoV2;
import com.github.sbugat.samplerest.model.User;

import ma.glasnost.orika.CustomMapper;

@Named
public class UserDtoV2Mapper extends CustomMapper<User, UserDtoV2> {
	// Nothing to customize
}
