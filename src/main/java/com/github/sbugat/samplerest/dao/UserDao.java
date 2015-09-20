package com.github.sbugat.samplerest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sbugat.samplerest.model.User;

public interface UserDao extends JpaRepository<User, Long> {

	public User findByUsername(final String username);
}
