package asd.inventory.controllers;

import asd.inventory.utils.functionality.JwtGenerator;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Path("/auth")
public class AuthController {

    @Inject
    JwtGenerator jwtGenerator;

    @GET
    @Path("/token")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String getToken() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("subject", "testUser");
        payload.put("issuer", "CustomIssuer");
        payload.put("role", "Manager");
        payload.put("groups", Arrays.asList("Admin", "User"));
        payload.put("app", "InventorySystem");
        payload.put("issuedAt", new Date());
        payload.put("expiration", new Date(System.currentTimeMillis() + 20 * 60 * 1000L));

        return jwtGenerator.generateToken(payload);
    }
}
