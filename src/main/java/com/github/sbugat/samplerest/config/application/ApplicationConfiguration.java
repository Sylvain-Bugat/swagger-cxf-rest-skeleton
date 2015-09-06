package com.github.sbugat.samplerest.config.application;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.github.sbugat.samplerest.config.cxf.CXFConfiguration;

@Configuration
@PropertySource("classpath:environment/${" + ApplicationConfiguration.ENVIRONMENT_TYPE + ':' + ApplicationConfiguration.DEFAULT_ENVIRONMENT_TYPE + "}/application-configuration.properties")
@Import(CXFConfiguration.class)
public class ApplicationConfiguration {

	static final String ENVIRONMENT_TYPE = "environment.type";
	static final String DEFAULT_ENVIRONMENT_TYPE = "development";

	@Inject
	private Environment environment;

	@Bean
	public String environmentType() {
		return environment.getProperty(ENVIRONMENT_TYPE);
	}
}
