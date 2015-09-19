package com.github.sbugat.samplerest.config.security;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.sbugat.samplerest.web.security.AuthenticationSuccessHandler;
import com.github.sbugat.samplerest.web.security.TokenAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Inject
	private ServletContext servletContext;

	@Inject
	public void configureGlobal(final AuthenticationManagerBuilder authentication) throws Exception {
		authentication.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin").password("password").roles("ADMIN");

		final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setSaltSource(new SaltSource() {

			@Override
			public Object getSalt(final UserDetails user) {
				return user.getUsername();
			}
		});
		authentication.authenticationProvider(daoAuthenticationProvider);
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		final String contextPath = servletContext.getContextPath();

		httpSecurity.csrf().disable() // Disable CSRF
				.addFilterAfter(new TokenAuthenticationFilter(contextPath + "/**"), UsernamePasswordAuthenticationFilter.class) // API token filter
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Session less

		.and().exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()) // Entry point

		// .and().formLogin().successHandler(authenticationSuccessHandler).loginProcessingUrl("/api/user/login").failureUrl("/login").usernameParameter("username").passwordParameter("password") // login access

		.and().authorizeRequests().antMatchers(contextPath + "/swagger/login", contextPath + "/swagger/jquery.min.js").permitAll() // Login and resources access
				.antMatchers(contextPath + "/*", contextPath + "/swagger/**").hasRole("ADMIN") // Admin access to Swagger
				.antMatchers(contextPath + "/**").hasAnyRole("USER", "ADMIN") // API access
				.antMatchers("/**").denyAll(); // Deny all other resources

	}
}
