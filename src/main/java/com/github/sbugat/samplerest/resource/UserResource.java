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

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.github.sbugat.samplerest.dao.UserDao;
import com.github.sbugat.samplerest.exception.ApiException;
import com.github.sbugat.samplerest.exception.NotFoundException;
import com.github.sbugat.samplerest.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Path("/user")
@Api(
		value = "/user",
		authorizations = { @Authorization("api_token") })
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Named
public class UserResource {

	@Inject
	private UserDao userDao;

	@POST
	@ApiOperation(
			value = "Create user",
			notes = "This can only be done by the logged in user.")
	public Response createUser(@ApiParam(
			value = "Created user object",
			required = true) final User user) {
		userDao.save(user);
		return Response.ok().entity("").build();
	}

	@PUT
	@Path("/{username}")
	@ApiOperation(
			value = "Updated user",
			notes = "This can only be done by the logged in user.")
	@ApiResponses(
			value = { @ApiResponse(
					code = 400,
					message = "Invalid user supplied"),
					@ApiResponse(
							code = 404,
							message = "User not found") })
	public Response updateUser(@ApiParam(
			value = "name that need to be updated",
			required = true) @PathParam("username") final String username,
			@ApiParam(
					value = "Updated user object",
					required = true) final User user) {
		final User originalUser = userDao.findByUsername(username);
		user.setId(originalUser.getId());
		userDao.save(user);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{username}")
	@ApiOperation(
			value = "Delete user",
			notes = "This can only be done by the logged in user.")
	@ApiResponses(
			value = { @ApiResponse(
					code = 400,
					message = "Invalid username supplied"),
					@ApiResponse(
							code = 404,
							message = "User not found") })
	public Response deleteUser(@ApiParam(
			value = "The name that needs to be deleted",
			required = true) @PathParam("username") final String username) throws NotFoundException {

		final User user = userDao.findByUsername(username);
		if (null != user) {
			userDao.delete(user);
			return Response.ok().build();
		} else {
			throw new NotFoundException(404, "User not found");
		}
	}

	@GET
	@Path("/{username}")
	@ApiOperation(
			value = "Get user by user name",
			notes = "This can only be done by the logged in user.",
			response = User.class)
	@ApiResponses(
			value = { @ApiResponse(
					code = 400,
					message = "Invalid username supplied"),
					@ApiResponse(
							code = 404,
							message = "User not found") })
	public Response getUserByName(@ApiParam(
			value = "The name that needs to be fetched. Use user1 for testing. ",
			required = true) @PathParam("username") final String username) throws ApiException {

		final User user = userDao.findByUsername(username);
		if (null != user) {
			return Response.ok().entity(user).build();
		} else {
			throw new NotFoundException(404, "User not found");
		}
	}
}