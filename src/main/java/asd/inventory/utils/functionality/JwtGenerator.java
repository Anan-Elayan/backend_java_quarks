package asd.inventory.utils.functionality;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class JwtGenerator {

    private static final Logger LOG = Logger.getLogger(JwtGenerator.class);

    public String generateToken(String username) {
        try {
            String token = Jwt.issuer("asd-inventory-system")       // who issued this token
                    .subject(username)                              // user identity
                    .groups(new HashSet<>(Arrays.asList("User", "Admin"))) // roles/groups
                    .claim("app", "InventorySystem")                // custom claim
                    .expiresAt(System.currentTimeMillis() / 1000 + 3600) // 1 hour expiry
                    .sign();

            LOG.info("✅ Generated JWT for user: " + username);
            LOG.info("🔑 JWT Token: " + token);
            System.out.println("JWT Token: " + token);

            return token;
        } catch (Exception e) {
            LOG.error("Error generating JWT: ", e);
            return null;
        }
    }
}
