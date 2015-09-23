package com.github.sbugat.samplerest.resource;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;

import com.github.sbugat.samplerest.exception.NotFoundException;
import com.google.common.io.Files;

import io.swagger.annotations.Api;

@Path("/swagger")
@Api(
		value = "/swagger",
		hidden = true)
@Named
public class SwaggerResource {

	@Inject
	private ApplicationContext context;

	@GET
	@Path("{path:.*}")
	public Response getFile(@PathParam("path") final String path) throws IOException, NotFoundException {

		final File file = context.getResource("/api/swagger/" + path).getFile();

		// Test if the requested file exists and if it can be read and if the file is "normal"
		if (!file.exists() || !file.canRead() || !file.isFile()) {
			throw new NotFoundException(404, "File not found");
		}

		// JavaScript file detection to set the correct MIME type to enable browser execution (blocked by recent browser otherwise)
		if (path.endsWith(".js")) {
			return Response.ok(Files.toByteArray(file), "application/javascript").build();
		}
		return Response.ok(Files.toByteArray(file)).build();
	}
}
