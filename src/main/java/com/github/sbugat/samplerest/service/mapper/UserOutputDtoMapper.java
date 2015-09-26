package com.github.sbugat.samplerest.service.mapper;

import javax.inject.Named;

import com.github.sbugat.samplerest.dto.UserOutputDto;
import com.github.sbugat.samplerest.model.User;

import ma.glasnost.orika.CustomMapper;

@Named
public class UserOutputDtoMapper extends CustomMapper<User, UserOutputDto> {
	// Nothing to customize
}
