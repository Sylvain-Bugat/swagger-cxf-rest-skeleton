package com.github.sbugat.samplerest.web.security;

import java.io.IOException;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.github.sbugat.samplerest.dao.UserTokenDao;
import com.github.sbugat.samplerest.model.UserToken;

@Named
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final XLogger log = XLoggerFactory.getXLogger(TokenAuthenticationFilter.class);

	private final String COOKIE_SECURITY_TOKEN = "api_token";

	@Inject
	private UserTokenDao userTokenDao;

	public TokenAuthenticationFilter() {
		super("/**");

		setAuthenticationManager(new NoOpAuthenticationManager());
		setAuthenticationSuccessHandler(new NoOpAuthenticationSuccessHandler());
	}

	/**
	 * Attempt to authenticate request - basically just pass over to another method to authenticate request headers
	 */
	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String token = null;
		if (null != request.getCookies()) {
			for (final Cookie cookie : request.getCookies()) {
				if (COOKIE_SECURITY_TOKEN.equals(cookie.getName())) {
					token = cookie.getValue();
				}
			}
		}

		if (token == null) {
			logger.info("No token found request:" + request.getRequestURI());
			throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "No Token"));
		}

		logger.info("token found:" + token + " request:" + request.getRequestURI());
		final AbstractAuthenticationToken userAuthenticationToken = authUserByToken(token);
		if (userAuthenticationToken == null) {
			throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));
		}
		return userAuthenticationToken;
	}

	/**
	 * authenticate the user based on token
	 *
	 * @return
	 */
	private AbstractAuthenticationToken authUserByToken(final String token) {

		final UserToken userToken = userTokenDao.findByAuthenticationToken(token);
		if (null == userToken) {
			return null;
		}

		final AbstractAuthenticationToken authToken = new AuthenticationToken(userToken);
		return authToken;
	}

	private boolean isLoginRequest(final HttpServletRequest request) {

		if (request.getRequestURI().equals("/swagger/login")) {
			return true;
		} else if (request.getRequestURI().equals("/swagger/jquery.min.js")) {
			return true;
		} else if (request.getRequestURI().equals("/auth/login")) {
			return true;
		}

		return false;
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response) || isLoginRequest(request)) {
			chain.doFilter(request, response);

			return;
		}

		logger.debug("Request is to process authentication");

		final Authentication authResult;

		try {
			authResult = attemptAuthentication(request, response);
			// sessionStrategy.onAuthentication(authResult, request, response);
		} catch (final InternalAuthenticationServiceException failed) {
			logger.error("An internal error occurred while trying to authenticate the user.", failed);
			unsuccessfulAuthentication(request, response, failed);

			return;
		} catch (final AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);

			return;
		}

		// Authentication success
		successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
}
