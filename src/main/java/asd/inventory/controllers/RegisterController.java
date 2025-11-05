package asd.inventory.controllers;


import asd.inventory.models.UserModel;
import asd.inventory.services.RegisterServices;
import asd.inventory.utils.constants.SystemPath;
import asd.inventory.utils.functionality.ErrorMessage;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path(SystemPath.register)
public class RegisterController {

    @Inject
    RegisterServices registerServices;

    @POST
    public Response register(UserModel userModel) {
        ErrorMessage successMessage = registerServices.register(userModel);
        return Response.status(Response.Status.OK)
                .entity(successMessage)
                .build();
    }
}
