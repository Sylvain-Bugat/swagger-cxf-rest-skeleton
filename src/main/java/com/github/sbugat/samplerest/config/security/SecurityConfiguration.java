package com.github.sbugat.samplerest.config.security;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.sbugat.samplerest.service.security.UserDetailsSecurityService;
import com.github.sbugat.samplerest.web.security.JSONUsernamePasswordAuthenticationFilter;
import com.github.sbugat.samplerest.web.security.TokenAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private UserDetailsSecurityService userDetailsSecurityService;

	@Inject
	private ServletContext servletContext;

	@Inject
	private JSONUsernamePasswordAuthenticationFilter jSONUsernamePasswordAuthenticationFilter;

	@Inject
	private TokenAuthenticationFilter tokenAuthenticationFilter;

	@Inject
	public void configureGlobal(final AuthenticationManagerBuilder authentication) throws Exception {

		final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		daoAuthenticationProvider.setUserDetailsService(userDetailsSecurityService);
		authentication.authenticationProvider(daoAuthenticationProvider);

		authentication.userDetailsService(userDetailsSecurityService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		final String contextPath = servletContext.getContextPath();

		httpSecurity.csrf().disable() // Disable CSRF
				.addFilter(jSONUsernamePasswordAuthenticationFilter) // Custom username/password filter
				.addFilterAfter(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // API token filter
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Session less

		.and().exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()) // Entry point

		.and().authorizeRequests().antMatchers(contextPath + "/auth/login", contextPath + "/swagger/login", contextPath + "/swagger/jquery.min.js").permitAll() // Login and Swagger login resources access
				.antMatchers(contextPath + "/*", contextPath + "/swagger/**").hasRole("ADMIN") // Admin access to Swagger
				.antMatchers(contextPath + "/**").hasAnyRole("USER", "ADMIN"); // API access
	}
}
