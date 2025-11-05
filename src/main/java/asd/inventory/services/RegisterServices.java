package asd.inventory.services;


import asd.inventory.models.UserModel;
import asd.inventory.repositories.LoginRepository;
import asd.inventory.repositories.RegisterRepository;
import asd.inventory.utils.functionality.ErrorMessage;
import asd.inventory.utils.functionality.JwtGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class RegisterServices {

    private static final Logger LOG = Logger.getLogger(LoginServices.class);

    @Inject
    RegisterRepository registerRepository;


    @Transactional
    public ErrorMessage register(UserModel userModel) {
        try{
            registerRepository.persist(userModel);
            LOG.info("Registration successful for user: " + userModel.getName());
            return new ErrorMessage("Registration successful", true, null);
        }catch (Exception e){
            LOG.error("Error during registration: " + e.getMessage());
            return new ErrorMessage("Internal error during registration", false, null);
        }
    }
}
