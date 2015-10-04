package com.github.sbugat.samplerest.web.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.github.sbugat.samplerest.model.UserToken;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class AuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -8551562133151501524L;

	private final String user;

	private final String token;

	public AuthenticationToken(final UserToken userToken) {
		super(toAuthorities(userToken.getUser().getRoles()));

		user = userToken.getUser().getUsername();
		token = userToken.getAuthenticationToken();
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	private static Collection<? extends GrantedAuthority> toAuthorities(final String roles) {

		final Collection<GrantedAuthority> authoritiesList = new ArrayList<>();

		for (final String role : Splitter.on(",").omitEmptyStrings().trimResults().split(Strings.nullToEmpty(roles))) {

			authoritiesList.add(new SimpleGrantedAuthority(role));
		}

		return authoritiesList;
	}
}
