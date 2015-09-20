package com.github.sbugat.samplerest.service.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.sbugat.samplerest.dao.UserDao;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

@Named
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Inject
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final com.github.sbugat.samplerest.model.User user = userDao.findByUsername(username);
		if (null == user) {
			return null;
		}

		return new User(username, user.getPassword(), true, true, true, true, toAuthorities(user.getRoles()));
	}

	private static Collection<? extends GrantedAuthority> toAuthorities(final String roles) {

		final Collection<GrantedAuthority> authoritiesList = new ArrayList<>();

		for (final String role : Splitter.on(",").omitEmptyStrings().trimResults().split(Strings.nullToEmpty(roles))) {

			authoritiesList.add(new SimpleGrantedAuthority(role));
		}

		return authoritiesList;
	}
}
