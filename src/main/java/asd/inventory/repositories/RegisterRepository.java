package asd.inventory.repositories;


import asd.inventory.models.UserModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RegisterRepository implements PanacheRepository<UserModel> {

    public void register(UserModel userModel) {
        persist(userModel);
    }

}
