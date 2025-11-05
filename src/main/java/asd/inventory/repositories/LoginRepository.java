package asd.inventory.repositories;

import asd.inventory.models.StockModel;
import asd.inventory.models.UserModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class LoginRepository implements PanacheRepository<UserModel> {


}
