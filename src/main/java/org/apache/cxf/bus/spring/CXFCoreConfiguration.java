package org.apache.cxf.bus.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.sbugat.samplerest.config.CoreConfiguration;

/**
 * CXF Spring configuration based on CXF core cxf.xml file.
 *
 * Used package is org.apache.cxf.bus.spring instead of applicative one because of package-protected constructor in Jsr250BeanPostProcessor class.
 *
 * @author Sylvain Bugat
 * @see org.apache.cxf.bus.spring.Jsr250BeanPostProcessor
 */
@Configuration
@Import(CoreConfiguration.class)
public class CXFCoreConfiguration {

	/**
	 * Construct the main CXF Spring bus.
	 *
	 * @return CXF Spring bus
	 */
	@Bean(destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}

	/**
	 * Construct the post processor bean to wire CXF bus.
	 *
	 * @return CXF Post processor
	 */
	@Bean(name = "org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor")
	public BusWiringBeanFactoryPostProcessor busWiringBeanFactoryPostProcessor() {
		return new BusWiringBeanFactoryPostProcessor();
	}

	/**
	 * Construct the CXF bus extension post processor.
	 *
	 * @return CXF Bus extension post processor
	 */
	@Bean(name = "org.apache.cxf.bus.spring.BusExtensionPostProcessor")
	public BusExtensionPostProcessor busExtensionPostProcessor() {
		return new BusExtensionPostProcessor();
	}

	/**
	 * Construct the annotation (JSR 250) post processor.
	 *
	 * @return CXF annotation post processor
	 */

	@Bean(name = "org.apache.cxf.bus.spring.Jsr250BeanPostProcessor")
	public Jsr250BeanPostProcessor jsr250BeanPostProcessor() {
		return new Jsr250BeanPostProcessor();
	}

}
