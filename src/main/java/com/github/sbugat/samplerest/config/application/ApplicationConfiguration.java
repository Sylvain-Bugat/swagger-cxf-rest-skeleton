package com.github.sbugat.samplerest.config.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.sbugat.samplerest.config.cxf.CXFConfiguration;

import io.swagger.jaxrs.config.BeanConfig;

@Configuration
@Import(CXFConfiguration.class)
public class ApplicationConfiguration {

	@Bean
	public BeanConfig swaggerConfig() {
		final BeanConfig beanConfig = new BeanConfig();
		beanConfig.setResourcePackage("com.github.sbugat.samplerest.resource");
		beanConfig.setVersion("1.0.0");
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/api");
		beanConfig.setTitle("Swagger CXF, JAX-RS 2.0 sample");
		beanConfig.setDescription("This is a Swagger CXF, JAX-RS 2.0 sample API.");
		beanConfig.setContact("Sylvain Bugat");
		beanConfig.setLicense("Apache 2.0");
		beanConfig.setLicenseUrl("https://github.com/Sylvain-Bugat/swagger-cxf-rest-skeleton/blob/master/LICENSE");
		beanConfig.setScan(true);
		return beanConfig;
	}
}
