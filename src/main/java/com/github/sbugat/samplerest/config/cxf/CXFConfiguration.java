package com.github.sbugat.samplerest.config.cxf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.apache.cxf.bus.spring.CXFCoreConfiguration;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.sbugat.samplerest.resource.SampleExceptionMapper;
import com.google.common.collect.Lists;

import io.swagger.jaxrs.listing.SwaggerSerializers;

@Configuration
@Import({ CXFCoreConfiguration.class })
public class CXFConfiguration {

	/** Spring context. */
	@Inject
	private ApplicationContext context;

	/* Providers. */

	/** Swagger provider. */
	@Inject
	private SwaggerSerializers swaggerSerializers;

	/** Jackson JSON provider. */
	@Inject
	private JacksonJaxbJsonProvider jacksonJaxbJsonProvider;

	/** Exception handler provider. */
	@Inject
	private SampleExceptionMapper sampleExceptionMapper;

	/**
	 * Contruct the CXF rest-service factory bean.
	 *
	 * @return CXF rest-service factory bean
	 */
	@Bean(initMethod = "create")
	public JAXRSServerFactoryBean jAXRSServerFactoryBean() {

		final JAXRSServerFactoryBean jAXRSServerFactoryBean = new JAXRSServerFactoryBean();
		jAXRSServerFactoryBean.setAddress("/");
		// Logging feature to display incoming/outgoing messages
		jAXRSServerFactoryBean.getFeatures().add(new LoggingFeature());

		final List<Object> serviceBeanslist = new ArrayList<>();
		// Get all bean with @Path annotation (resources implementation)
		for (final Map.Entry<String, Object> entry : context.getBeansWithAnnotation(Path.class).entrySet()) {

			serviceBeanslist.add(entry.getValue());
		}
		// Set resource beans
		jAXRSServerFactoryBean.setServiceBeans(serviceBeanslist);

		// Set providers
		final List<Object> providerslist = Lists.newArrayList(swaggerSerializers, jacksonJaxbJsonProvider, sampleExceptionMapper);
		jAXRSServerFactoryBean.setProviders(providerslist);

		return jAXRSServerFactoryBean;
	}
}
