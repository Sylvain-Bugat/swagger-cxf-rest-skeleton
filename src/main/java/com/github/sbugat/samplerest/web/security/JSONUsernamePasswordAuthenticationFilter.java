package com.github.sbugat.samplerest.web.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Named
public class JSONUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Inject
	private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;

	@Inject
	private AuthenticationManager authenticationManagerBean;

	public JSONUsernamePasswordAuthenticationFilter() {
		setFilterProcessesUrl("/auth/login");
	}

	@PostConstruct
	public void postConstruct() {
		setAuthenticationManager(authenticationManagerBean);
		setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {

		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		final UserData userData;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			userData = mapper.readValue(request.getInputStream(), UserData.class);
		} catch (final IOException e) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userData.username, userData.password);
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private static final class UserData {

		public String username;
		public String password;
	}
}
