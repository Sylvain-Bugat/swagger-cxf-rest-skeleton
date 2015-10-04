package com.github.sbugat.samplerest.service.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLogger.Level;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.sbugat.samplerest.dao.UserDao;
import com.github.sbugat.samplerest.model.User;
import com.github.sbugat.samplerest.resource.UserResource;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

@Named
public class UserDetailsSecurityService implements UserDetailsService {

	/** SLF4J Xlogger. */
	private static final XLogger logger = XLoggerFactory.getXLogger(UserResource.class);

	@Inject
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		logger.info("Load username {} details", username);
		final User user = userDao.findByUsername(username);
		if (null == user) {
			final UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException(username);
			logger.throwing(Level.TRACE, usernameNotFoundException);
			throw usernameNotFoundException;
		}

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, toAuthorities(user.getRoles()));
	}

	private static Collection<? extends GrantedAuthority> toAuthorities(final String roles) {

		final Collection<GrantedAuthority> authoritiesList = new ArrayList<>();

		for (final String role : Splitter.on(",").omitEmptyStrings().trimResults().split(Strings.nullToEmpty(roles))) {

			authoritiesList.add(new SimpleGrantedAuthority(role));
		}

		return authoritiesList;
	}
}
