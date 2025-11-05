package asd.inventory.utils.functionality;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@ApplicationScoped
public class JwtGenerator {

    private static final Logger LOG = Logger.getLogger(JwtGenerator.class);

    private static final String SECRET = "mySuperSecretKey1234567890!@#ABCDEF";
    private static final long DEFAULT_EXPIRATION = 10 * 60 * 1000L; // 10 minutes

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());


    public String generateToken(Map<String, Object> payloadMap) {
        var builder = Jwts.builder();

        if (payloadMap.containsKey("subject")) {
            builder.setSubject(payloadMap.get("subject").toString());
            payloadMap.remove("subject");
        }

        if (payloadMap.containsKey("issuer")) {
            builder.setIssuer(payloadMap.get("issuer").toString());
            payloadMap.remove("issuer");
        } else {
            builder.setIssuer("asd-inventory-system"); // Default
        }

        if (payloadMap.containsKey("issuedAt") && payloadMap.get("issuedAt") instanceof Date) {
            builder.setIssuedAt((Date) payloadMap.get("issuedAt"));
            payloadMap.remove("issuedAt");
        } else {
            builder.setIssuedAt(new Date());
        }

        if (payloadMap.containsKey("expiration") && payloadMap.get("expiration") instanceof Date) {
            builder.setExpiration((Date) payloadMap.get("expiration"));
            payloadMap.remove("expiration");
        } else {
            builder.setExpiration(new Date(System.currentTimeMillis() + DEFAULT_EXPIRATION));
        }


        payloadMap.forEach(builder::claim);

        String token = builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        LOG.info("Generated JWT: " + token);
        return token;
    }

    public Key getKey() {
        return key;
    }
}
