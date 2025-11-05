package asd.inventory.utils.functionality;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Provider
@ApplicationScoped
@Priority(1)
@PreMatching
public class GlobalRequestFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(GlobalRequestFilter.class);



    @Inject
    JwtGenerator jwtGenerator;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {





        String path = requestContext.getUriInfo().getPath();
        LOG.info("🔍 Incoming request path: " + path);
        if (shouldSkipJwtCheck(path)) return;
        String token = extractToken(requestContext);
        if (token == null) return;
        if (!verifyToken(token)) {
            abort(requestContext, "Invalid or expired token");
        }
    }


    private boolean shouldSkipJwtCheck(String path) {
        return path.contains("/auth/token");
    }


    private String extractToken(ContainerRequestContext ctx) {
        String authHeader = ctx.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            LOG.warn("Missing or invalid Authorization header");
            abort(ctx, "Authorization header missing or malformed");
            return null;
        }
        return authHeader.substring("Bearer ".length()).trim();
    }


    private boolean verifyToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtGenerator.getKey())
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            LOG.info("JWT verified successfully. User: " + claims.getSubject() +
                    ", groups: " + claims.get("groups"));
            return true;
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature: " + e.getMessage());
            return false;
        } catch (Exception e) {
            LOG.error("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }


    private void abort(ContainerRequestContext ctx, String message) {
        ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\":\"" + message + "\"}")
                .build());
    }
}
