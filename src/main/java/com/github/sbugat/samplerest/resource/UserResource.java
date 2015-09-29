package com.github.sbugat.samplerest.resource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.ext.XLoggerFactory;

import com.github.sbugat.samplerest.dao.UserDao;
import com.github.sbugat.samplerest.dto.UserDto;
import com.github.sbugat.samplerest.dto.UserOutputDtoV2;
import com.github.sbugat.samplerest.exception.ApiException;
import com.github.sbugat.samplerest.exception.BadRequestException;
import com.github.sbugat.samplerest.exception.NotFoundException;
import com.github.sbugat.samplerest.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/user")
@Api("/user")
@Named
public class UserResource extends GenericResource {

	private static final Logger logger = XLoggerFactory.getXLogger(UserResource.class);

	@Inject
	private UserDao userDao;

	@POST
	@ApiOperation(
			value = "Create user",
			notes = "This can only be done by the logged in user.")
	@ApiResponses(
			value = @ApiResponse(
					code = 400,
					message = "Invalid user supplied") )
	public Response createUser(@ApiParam(
			value = "Created user object",
			required = true) final UserDto userDto) throws BadRequestException {
		final User user = orikaBeanMapper.map(userDto, User.class);
		try {
			userDao.save(user);
		} catch (final Exception e) {
			throw new BadRequestException(400, "Error inserting user");
		}
		return Response.ok().build();
	}

	@PUT
	@Path("/{username}")
	@ApiOperation(
			value = "Update user",
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
					required = true) final UserDto userDto) {
		final User originalUser = userDao.findByUsername(username);
		final User user = orikaBeanMapper.map(userDto, User.class);
		user.setId(originalUser.getUuid());
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
	@Path("/{usernameV1}")
	@Produces(GenericResource.API_VERSION1_MEDIA_TYPE)
	@ApiOperation(
			value = "Get user by user name",
			notes = "This can only be done by the logged in user.",
			response = UserDto.class,
			tags = "user-deprecated")
	@ApiResponses(
			value = { @ApiResponse(
					code = 400,
					message = "Invalid username supplied"),
					@ApiResponse(
							code = 404,
							message = "User not found") })
	public Response getUserByName(@ApiParam(
			value = "The name that needs to be fetched. Use user1 for testing. ",
			required = true) @PathParam("usernameV1") final String username) throws ApiException {

		final User user = userDao.findByUsername(username);
		final UserDto userDto = orikaBeanMapper.map(user, UserDto.class);
		if (null != user) {
			return Response.ok().entity(userDto).build();
		} else {
			throw new NotFoundException(404, "User not found");
		}
	}

	@GET
	@Path("/{username}")
	@Produces(GenericResource.API_VERSION2_MEDIA_TYPE)
	@ApiOperation(
			value = "Get user by user name",
			notes = "This can only be done by the logged in user.",
			response = UserOutputDtoV2.class)
	@ApiResponses(
			value = { @ApiResponse(
					code = 400,
					message = "Invalid username supplied"),
					@ApiResponse(
							code = 404,
							message = "User not found") })
	public Response getUserByName2(@ApiParam(
			value = "The name that needs to be fetched. Use user1 for testing. ",
			required = true) @PathParam("username") final String username) throws ApiException {

		final User user = userDao.findByUsername(username);
		final UserOutputDtoV2 userOutputDtoV2 = orikaBeanMapper.map(user, UserOutputDtoV2.class);
		userOutputDtoV2.addLinks(buildLinks());
		if (null != user) {
			return Response.ok().entity(userOutputDtoV2).links(buildLinks()).build();
		} else {
			throw new NotFoundException(404, "User not found");
		}
	}

	private Link[] buildLinks() {
		final Link[] links = { Link.fromUriBuilder(uriInfo.getRequestUriBuilder()).rel("self").build(), // Self link
				Link.fromUriBuilder(uriInfo.getRequestUriBuilder()).rel("update").build(), // Update link
				Link.fromUriBuilder(uriInfo.getRequestUriBuilder()).rel("delete").build() // Delete link
		};
		return links;
	}
}
