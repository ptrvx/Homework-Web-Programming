package coupons.controllers;

import coupons.binding.Secured;
import coupons.dtos.UserDTO;
import coupons.enums.Privilege;
import coupons.exceptions.DataException;
import coupons.exceptions.MyException;
import coupons.mappers.UserMapper;
import coupons.models.User;
import coupons.requests.CreateUserRequest;
import coupons.requests.LoginRequest;
import coupons.responses.LoginResponse;
import coupons.services.UserService;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserController {

    private UserService userService = new UserService();

    @GET
    @Secured({Privilege.ADMINISTRATOR, Privilege.OPERATOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDTO get(ContainerRequestContext requestContext) {
        User user = (User)requestContext.getProperty("user");
        return UserMapper.instance.map(user);
    }

    @POST
    @Secured({Privilege.ADMINISTRATOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDTO create(@Valid CreateUserRequest request) {
        return userService.create(
                request.getFirstName(),
                request.getLastName(),
                request.getPrivilege(),
                request.getUsername(),
                request.getPassword()
        );
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LoginResponse login(@Valid LoginRequest request) throws DataException, MyException {
        String token = userService.login(request.getUsername(), request.getPassword());
        UserDTO user = userService.get(request.getUsername());
        return new LoginResponse(token, user);
    }


}
