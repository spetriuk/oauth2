package com.petriuk.rest;

import com.petriuk.dto.UserDto;
import com.petriuk.service.UserExistException;
import com.petriuk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
@Path("user")
public class UserResource extends SpringBeanAutowiringSupport {

    @Autowired
    private UserService userService;

    @GET
    @Path("{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("login") String login) {
        UserDto userDto = userService.getUserDto(login);
        return Response.status(Response.Status.OK).entity(userDto).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> getAllUsers() {
        return userService.getAllUsersAsDto();
    }

    @GET
    @Path("roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllRoles() {
        return userService.getAllRoles();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(
        @Valid @ConvertGroup(to = UserDto.CreateGroup.class) UserDto userDto,
        @Context UriInfo uriInfo) {
        try {
            userService.addUser(userDto.makeUser());
        } catch (UserExistException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.status(Response.Status.CREATED.getStatusCode())
            .header("Location", String
                .format("%s/%s", uriInfo.getAbsolutePath().toString(),
                    userDto.getLogin())).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUser(
        @Valid @ConvertGroup(to = UserDto.EditGroup.class) UserDto userDto,
        @Context UriInfo uriInfo) {

        userService.updateUser(userDto.makeUser());

        return Response.status(Response.Status.NO_CONTENT.getStatusCode())
            .header("Location", String
                .format("%s/%s", uriInfo.getAbsolutePath().toString(),
                    userDto.getLogin())).build();
    }

    @DELETE
    @Path("{login}")
    public Response deleteUser(@PathParam("login") String login) {
        userService.deleteUser(login);
        return Response.status(Response.Status.NO_CONTENT.getStatusCode())
            .build();
    }

}
