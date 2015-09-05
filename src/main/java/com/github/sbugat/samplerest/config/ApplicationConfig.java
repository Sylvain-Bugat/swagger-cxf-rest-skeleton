package com.github.sbugat.samplerest.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.github.sbugat.samplerest")
@PropertySource("classpath:application-configuration.properties")
public class ApplicationConfig {

	@Inject
	private Environment environment;

	@Bean
	public static PropertySourcesPlaceholderConfigurer ppc() {
		// instantiate, configure and return ppc ...
		return new PropertySourcesPlaceholderConfigurer();
	}
}
