package asd.inventory.repositories;

import asd.inventory.models.StockModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class StocksRepository implements PanacheRepository<StockModel> {

    public void addStockItem(StockModel stockModel) {
        persist(stockModel);
    }

    public List<StockModel> getAllStocks() {
        return listAll();
    }

    public boolean deleteStockItem(Long id) {
        long deleted = delete("id", id);
        return deleted > 0;
    }

}
