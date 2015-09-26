package com.github.sbugat.samplerest.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.github.sbugat.samplerest.service.OrikaBeanMapper;

@Consumes({ GenericResource.API_VERSION1_MEDIA_TYPE, GenericResource.API_VERSION2_MEDIA_TYPE })
@Produces({ GenericResource.API_VERSION1_MEDIA_TYPE, GenericResource.API_VERSION2_MEDIA_TYPE })
public abstract class GenericResource {

	protected final static String API_VERSION1_MEDIA_TYPE = "application/apiv1+json";

	protected final static String API_VERSION2_MEDIA_TYPE = "application/apiv2+json";

	@Inject
	protected OrikaBeanMapper orikaBeanMapper;

	@Context
	protected UriInfo uriInfo;
}
