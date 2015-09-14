package com.github.sbugat.samplerest.config.security;

import javax.inject.Inject;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.sbugat.samplerest.web.security.AuthenticationSuccessHandler;
import com.github.sbugat.samplerest.web.security.TokenAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Inject
	public void configureGlobal(final AuthenticationManagerBuilder authentication) throws Exception {
		authentication.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin").password("password").roles("ADMIN");
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		httpSecurity// .addFilterAfter(new TokenAuthenticationFilter("/api/**"), UsernamePasswordAuthenticationFilter.class).antMatcher("/api/**") // API token filter
				.csrf().disable() // Disable CSRF
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Session less

		.and().exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()) // Entry point

		.and()// .addFilterBefore(new TokenAuthenticationFilter("/api/**"), UsernamePasswordAuthenticationFilter.class).authorizeRequests()// API token filter
				.authorizeRequests().antMatchers("/api/*").hasRole("ADMIN") // Admin access
				.antMatchers("/api/**").hasRole("USER") // User access
				.antMatchers("/lib/**").permitAll() //

		.and().formLogin().successHandler(authenticationSuccessHandler).failureUrl("/login").usernameParameter("username").passwordParameter("password") // login access
				.and().addFilterAfter(new TokenAuthenticationFilter("/api/**"), UsernamePasswordAuthenticationFilter.class);
				// .and().authorizeRequests().antMatchers("/api/**").access("hasRole('ROLE_USER')") // User access

		// .and().authorizeRequests().antMatchers("/swagger/**").access("hasRole('ROLE_ADMIN')").anyRequest().authenticated() // Admin access

		// .and().addFilterBefore(new TokenAuthenticationFilter("/api/**"), UsernamePasswordAuthenticationFilter.class).antMatcher("/api/**").anonymous() // API token filter
		// .and().logout(); // Logout

		// httpSecurity.addFilterBefore(new TokenAuthenticationFilter2("/swagger/**"), AnonymousAuthenticationFilter.class).antMatcher("/swagger/**"); // API token filter
	}
}
