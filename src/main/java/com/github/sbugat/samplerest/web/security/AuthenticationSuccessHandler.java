package com.github.sbugat.samplerest.web.security;

import java.io.IOException;

import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Named
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	protected String determineTargetUrl(final HttpServletRequest request, final HttpServletResponse response) {
		final String context = request.getContextPath();
		final String fullURL = request.getRequestURI();
		final String url = fullURL.substring(fullURL.indexOf(context) + context.length());
		return url;
	}

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
		final String url = determineTargetUrl(request, response);
		request.getRequestDispatcher(url).forward(request, response);
	}
}
