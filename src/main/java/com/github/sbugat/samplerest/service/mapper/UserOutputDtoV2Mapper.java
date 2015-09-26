package com.github.sbugat.samplerest.service.mapper;

import javax.inject.Named;

import com.github.sbugat.samplerest.dto.UserOutputDtoV2;
import com.github.sbugat.samplerest.model.User;

import ma.glasnost.orika.CustomMapper;

@Named
public class UserOutputDtoV2Mapper extends CustomMapper<User, UserOutputDtoV2> {
	// Nothing to customize
}
