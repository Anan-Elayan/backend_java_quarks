package asd.inventory.utils.functionality;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;

@Provider
public class GlobalRequestFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(GlobalRequestFilter.class);

    @Inject
    JWTParser jwtParser;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path = requestContext.getUriInfo().getPath();

        if (path.contains("/auth/token")) {
            LOG.info("Skipping JWT check for /auth/token");
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            LOG.warn("Missing or invalid Authorization header");
            abort(requestContext, "Authorization header missing or malformed");
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();
        try {
            jwtParser.parse(token);
            LOG.info("JWT verified successfully");
        } catch (ParseException e) {
            LOG.error("Invalid JWT token: " + e.getMessage());
            abort(requestContext, "Invalid or expired token");
        }
    }


    private void abort(ContainerRequestContext ctx, String message) {
        ctx.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"" + message + "\"}")
                        .build()
        );
    }
}
