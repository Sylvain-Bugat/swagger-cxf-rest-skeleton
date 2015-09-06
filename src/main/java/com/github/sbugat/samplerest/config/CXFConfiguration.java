package com.github.sbugat.samplerest.config;

import java.util.List;

import javax.inject.Inject;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.sbugat.samplerest.resource.BookResource;
import com.github.sbugat.samplerest.resource.SampleExceptionMapper;
import com.github.sbugat.samplerest.resource.UserResource;
import com.google.common.collect.Lists;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@Configuration
@Import(ApplicationConfig.class)
public class CXFConfiguration {

	/* Service-resources beans. */

	@Inject
	private UserResource userResource;

	@Inject
	private BookResource bookResource;

	@Inject
	private ApiListingResource apiListingResource;

	@Inject
	private SwaggerSerializers swaggerSerializers;

	@Inject
	private JacksonJaxbJsonProvider jacksonJaxbJsonProvider;

	@Inject
	private SampleExceptionMapper sampleExceptionMapper;

	@Bean(initMethod = "create")
	public JAXRSServerFactoryBean jAXRSServerFactoryBean() {

		final JAXRSServerFactoryBean jAXRSServerFactoryBean = new JAXRSServerFactoryBean();
		jAXRSServerFactoryBean.setAddress("/");
		jAXRSServerFactoryBean.getFeatures().add(new LoggingFeature());

		final List<Object> serviceBeanslist = Lists.newArrayList(userResource, bookResource, apiListingResource);
		jAXRSServerFactoryBean.setServiceBeans(serviceBeanslist);

		final List<Object> providerslist = Lists.newArrayList(swaggerSerializers, jacksonJaxbJsonProvider, sampleExceptionMapper);
		jAXRSServerFactoryBean.setProviders(providerslist);

		return jAXRSServerFactoryBean;
	}
}
