package com.github.sbugat.samplerest.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sbugat.samplerest.model.User;

public interface UserDao extends JpaRepository<User, UUID> {

	User findByUsername(final String username);
}
