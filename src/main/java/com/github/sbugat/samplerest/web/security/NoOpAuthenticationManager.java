package com.github.sbugat.samplerest.web.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class NoOpAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		return authentication;
	}

}
