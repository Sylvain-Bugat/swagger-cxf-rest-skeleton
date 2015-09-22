/**
 *  Copyright 2015 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.sbugat.samplerest.resource;

import java.util.Date;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.github.sbugat.samplerest.data.UserData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Path("/auth")
@Api(
		value = "/auth",
		authorizations = { @Authorization("api_token") })
@Consumes("application/json")
@Produces("application/json")
@Named
public class AuthenticationResource {

	static UserData userData = new UserData();

	@POST
	@Path("/login")
	@ApiOperation(
			value = "Logs user into the system",
			response = String.class)
	@ApiResponses(
			value = { @ApiResponse(
					code = 400,
					message = "Invalid username/password supplied") })
	public Response loginUser(@ApiParam(
			value = "The user name for login",
			required = true) @QueryParam("username") final String username,
			@ApiParam(
					value = "The password for login in clear text",
					required = true) @QueryParam("password") final String password) {

		final Cookie cookie = new Cookie("api_token", "123456", "/", null);
		final NewCookie newCookie = new NewCookie(cookie, "comment", -1, null, false, true);
		return Response.ok().cookie(newCookie).build();
	}

	@GET
	@Path("/logout")
	@ApiOperation(
			value = "Logs out current logged in user session")
	public Response logoutUser() {
		final Cookie cookie = new Cookie("api_token", "", "/", null);
		final NewCookie newCookie = new NewCookie(cookie, "", 0, new Date(0), false, true);
		return Response.ok().cookie(newCookie).build();
	}
}
