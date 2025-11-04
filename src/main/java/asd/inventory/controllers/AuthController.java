package asd.inventory.controllers;

import asd.inventory.utils.functionality.JwtGenerator;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
public class AuthController {

    @Inject
    JwtGenerator jwtGenerator;

    @GET
    @Path("/token")
    @Produces(MediaType.TEXT_PLAIN)
    public String getToken() {
        String token = jwtGenerator.generateToken("testUser");
        return token != null ? token : "Token not generated, Token is Null! ";
    }
}
