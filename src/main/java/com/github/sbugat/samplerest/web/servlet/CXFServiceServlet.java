package com.github.sbugat.samplerest.web.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.apache.cxf.transport.servlet.CXFServlet;

@WebServlet(
		name = "CXFServiceServlet",
		description = "CXF Service Servlet",
		loadOnStartup = 1,
		initParams = { @WebInitParam(
				name = "swagger.api.basepath",
				value = "/"),
				@WebInitParam(
						name = "swagger.security.filter",
						value = "ApiAuthorizationFilterImpl") },
		urlPatterns = "/*")
public class CXFServiceServlet extends CXFServlet {

	/** Serial UID. */
	private static final long serialVersionUID = 2447415176072920775L;
}
