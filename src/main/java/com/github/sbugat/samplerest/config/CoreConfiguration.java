package com.github.sbugat.samplerest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.sbugat.samplerest.resource.SampleExceptionMapper;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@Configuration
@ComponentScan("com.github.sbugat.samplerest")
public class CoreConfiguration {

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
