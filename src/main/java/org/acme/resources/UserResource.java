package org.acme.resources;

import org.acme.domain.User;
import org.acme.domain.dto.UserDto;
import org.acme.mapping.UserMapper;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserResource {

    /*
    See https://github.com/coding-daddy-xyz/example-quarkus-postgresql
     */

    @Inject
    UserMapper userMapper;

    @Transactional
    @POST
    public Response create(UserDto userDto) {
        User user = userMapper.fromResource(userDto);
        user.persistAndFlush();
        return Response.ok(userMapper.toResource(user)).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(User.<User>findAll().stream().map(u -> userMapper.toResource(u)).collect(Collectors.toList())).build();
    }

    @DELETE
    @Path("/{username}")
    public Response delete(@PathParam("username") String username) {
        return User.findByUsername(username)
                .map(u -> { u.delete(); return Response.ok(); })
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/{username}")
    public Response find(@PathParam("username") String username) {
        return User.findByUsername(username)
                .map(u -> Response.ok(userMapper.toResource(u)))
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}
