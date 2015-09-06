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
@PropertySource("classpath:env/${" + ApplicationConfig.ENVIRONMENT_TYPE + ':' + ApplicationConfig.DEFAULT_ENVIRONMENT_TYPE + "}/application-configuration.properties")
public class ApplicationConfig {

	static final String ENVIRONMENT_TYPE = "environment.type";
	static final String DEFAULT_ENVIRONMENT_TYPE = "dev";

	@Inject
	private Environment environment;

	@Bean
	public String environmentType() {
		return environment.getProperty(ENVIRONMENT_TYPE);
	}

	/**
	 * Return the PropertySourcesPlaceholderConfigurer before properties file loading (static method).
	 *
	 * @return new PropertySourcesPlaceholderConfigurer instance
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		// instantiate, configure and return ppc ...
		return new PropertySourcesPlaceholderConfigurer();
	}
}
