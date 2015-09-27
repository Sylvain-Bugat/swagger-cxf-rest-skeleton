package com.github.sbugat.samplerest.web.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.github.sbugat.samplerest.service.security.AuthenticationTokenService;

@Named
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public static String TOKEN_REQUEST_ATTRIBUTE = LoginAuthenticationSuccessHandler.class.getName();

	@Inject
	private AuthenticationTokenService authenticationTokenService;

	@Override
	protected String determineTargetUrl(final HttpServletRequest request, final HttpServletResponse response) {
		final String context = request.getContextPath();
		final String fullURL = request.getRequestURI();
		final String url = fullURL.substring(fullURL.indexOf(context) + context.length());
		return url;
	}

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			final String token = authenticationTokenService.generateAuthenticationToken(userDetails.getUsername());
			if (null != token) {

				request.setAttribute(TOKEN_REQUEST_ATTRIBUTE, token);
			}
		}

		final String url = determineTargetUrl(request, response);
		request.getRequestDispatcher(url).forward(request, response);
	}
}
