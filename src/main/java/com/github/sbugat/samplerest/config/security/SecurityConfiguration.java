package com.github.sbugat.samplerest.config.security;

import javax.inject.Inject;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.github.sbugat.samplerest.web.security.RestAuthenticationEntryPoint;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Inject
	public void configureGlobal(final AuthenticationManagerBuilder authentication) throws Exception {
		authentication.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin").password("password").roles("ADMIN");
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		/*
		 * httpSecurity.csrf().disable() // Disable CSRF .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and() // Entry point // .authorizeRequests().antMatchers("/*").access("hasRole('ROLE_ADMIN')").anyRequest().authenticated() // Admin access .authorizeRequests().antMatchers("/api/**").access("hasRole('ROLE_USER')").anyRequest().authenticated() // User access .and().formLogin().loginProcessingUrl("/api/user/login").usernameParameter("username").passwordParameter("password")// login access .and().logout(); // Logout
		 */
		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/api/**").access("hasRole('ROLE_USER')").and().formLogin();
	}
}
