package com.github.sbugat.samplerest.service;

import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

@Named
public class OrikaBeanMapper extends ConfigurableMapper implements ApplicationContextAware {

	private MapperFactory mapperfactory;
	private ApplicationContext applicationContext;

	public OrikaBeanMapper() {
		super(false);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContextArg) throws BeansException {
		applicationContext = applicationContextArg;
		init();
	}

	@Override
	protected void configure(final MapperFactory mapperfactoryArg) {
		mapperfactory = mapperfactoryArg;
		addAllSpringBeans(applicationContext);
	}

	@Override
	protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
		// Nothing to do?
	}

	/**
	 * Constructs and registers a {@link ClassMapBuilder} into the {@link MapperFactory} using a {@link Mapper}.
	 *
	 * @param mapper
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addMapper(final Mapper<?, ?> mapper) {
		mapperfactory.classMap(mapper.getAType(), mapper.getBType()).byDefault().customize((Mapper) mapper).register();
	}

	public void addConverter(final Converter<?, ?> converter) {
		mapperfactory.getConverterFactory().registerConverter(converter);
	}

	@SuppressWarnings("rawtypes")
	private void addAllSpringBeans(final ApplicationContext applicationContext) {
		final Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
		for (final Mapper mapper : mappers.values()) {
			addMapper(mapper);
		}
		final Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
		for (final Converter converter : converters.values()) {
			addConverter(converter);
		}
	}
}
