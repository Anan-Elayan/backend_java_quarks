package asd.inventory.services;

import asd.inventory.models.UserModel;
import asd.inventory.repositories.LoginRepository;
import asd.inventory.utils.functionality.ErrorMessage;
import asd.inventory.utils.functionality.JwtGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class LoginServices {

    private static final Logger LOG = Logger.getLogger(LoginServices.class);

    @Inject
    LoginRepository usersRepository;

    @Inject
    JwtGenerator jwtGenerator;

    @Transactional
    public ErrorMessage checkLogin(UserModel userModel) {
        try {
            var user = usersRepository.find("name = ?1 and password = ?2",
                            userModel.getName(), userModel.getPassword())
                    .firstResultOptional();
            if (user.isEmpty()) {
                return new ErrorMessage("Invalid username or password", false, null);
            }
            Long userId = user.get().getId();

            Map<String, Object> payload = new HashMap<>();
            payload.put("subject", userModel.getName());
            payload.put("issuer", "InventorySystem");
            payload.put("role", "User");
            payload.put("user", Map.of("id", userId));
            payload.put("app", "InventorySystem");
            payload.put("issuedAt", new Date());
            payload.put("expiration", new Date(System.currentTimeMillis() + 60 * 1000L));

            String token = jwtGenerator.generateToken(payload);

            LOG.info("Login successful for user: " + userModel.getName());

            return new ErrorMessage("Login successful", true, token);

        } catch (Exception e) {
            LOG.error("Error during login: " + e.getMessage());
            return new ErrorMessage("Internal error during login", false, null);
        }
    }


}
