package com.github.sbugat.samplerest.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.sbugat.samplerest.resource.SampleExceptionMapper;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@ComponentScan("com.github.sbugat.samplerest")
@EnableEncryptableProperties
@PropertySource("classpath:environment/${" + CoreConfiguration.ENVIRONMENT_TYPE + ':' + CoreConfiguration.DEFAULT_ENVIRONMENT_TYPE + "}/application-configuration.properties")
@Import({ JpaConfiguration.class })
public class CoreConfiguration {

	static final String ENVIRONMENT_TYPE = "environment.type";
	static final String DEFAULT_ENVIRONMENT_TYPE = "development";

	@Inject
	private Environment environment;

	@Bean
	public String environmentType() {
		return environment.getProperty(ENVIRONMENT_TYPE);
	}

	@Bean
	public SwaggerSerializers swaggerSerializers() {
		return new SwaggerSerializers();
	}

	@Bean
	public JacksonJaxbJsonProvider jacksonJaxbJsonProvider() {
		return new JacksonJaxbJsonProvider();
	}

	@Bean
	public SampleExceptionMapper sampleExceptionMapper() {
		return new SampleExceptionMapper();
	}

	@Bean
	public ApiListingResource apiListingResource() {
		return new ApiListingResource();
	}
}
