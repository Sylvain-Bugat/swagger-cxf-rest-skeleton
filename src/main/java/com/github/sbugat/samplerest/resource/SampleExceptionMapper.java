package com.github.sbugat.samplerest.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.ext.XLoggerFactory;

import com.github.sbugat.samplerest.exception.ApiException;
import com.github.sbugat.samplerest.exception.BadRequestException;
import com.github.sbugat.samplerest.exception.NotFoundException;
import com.github.sbugat.samplerest.model.ApiResponse;

@Provider
public class SampleExceptionMapper implements ExceptionMapper<Exception> {

	private static final Logger logger = XLoggerFactory.getXLogger(SampleExceptionMapper.class);

	@Override
	public Response toResponse(final Exception exception) {
		if (exception instanceof javax.ws.rs.WebApplicationException) {
			final javax.ws.rs.WebApplicationException e = (javax.ws.rs.WebApplicationException) exception;
			return Response.status(e.getResponse().getStatus()).entity(new ApiResponse(e.getResponse().getStatus(), exception.getMessage())).type("application/json").build();
		} else if (exception instanceof com.fasterxml.jackson.core.JsonParseException) {
			return Response.status(Status.BAD_REQUEST).entity(new ApiResponse(400, "bad input")).build();
		} else if (exception instanceof NotFoundException) {
			return Response.status(Status.NOT_FOUND).entity(new ApiResponse(ApiResponse.ERROR, exception.getMessage())).type("application/json").build();
		} else if (exception instanceof BadRequestException) {
			return Response.status(Status.BAD_REQUEST).entity(new ApiResponse(ApiResponse.ERROR, exception.getMessage())).type("application/json").build();
		} else if (exception instanceof ApiException) {
			return Response.status(Status.BAD_REQUEST).entity(new ApiResponse(ApiResponse.ERROR, exception.getMessage())).type("application/json").build();
		} else {
			logger.error("Internal server error", exception);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ApiResponse(500, "Internal server error")).type("application/json").build();
		}
	}
}