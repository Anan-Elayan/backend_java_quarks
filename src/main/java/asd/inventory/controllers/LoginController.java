package asd.inventory.controllers;
import asd.inventory.models.UserModel;
import asd.inventory.services.LoginServices;
import asd.inventory.utils.constants.SystemPath;
import asd.inventory.utils.functionality.ErrorMessage;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path(SystemPath.LOGIN)
public class LoginController {

    @Inject
    LoginServices loginServices;

    @POST
    public Response loginCheck(UserModel userModel) {
        ErrorMessage successMessage = loginServices.checkLogin(userModel);
        return Response.status(Response.Status.OK)
                .entity(successMessage)
                .build();
    }
}
