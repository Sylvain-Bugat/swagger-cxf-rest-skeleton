package com.github.sbugat.samplerest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sbugat.samplerest.model.User;
import com.github.sbugat.samplerest.model.UserToken;

public interface UserTokenDao extends JpaRepository<UserToken, Long> {

	UserToken findByAuthenticationToken(final String authenticationToken);

	UserToken findByUser(final User user);
}
