package asd.inventory.services;

import asd.inventory.models.StockModel;
import asd.inventory.models.UserModel;
import asd.inventory.repositories.StocksRepository;
import asd.inventory.utils.functionality.ErrorMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class StockServices {

    @Inject
    StocksRepository repository;

    @Transactional
    public ErrorMessage addStockItem(StockModel stockModel, UserModel user) {

        stockModel.setUser(user);
        repository.addStockItem(stockModel);

        return new ErrorMessage(
                "Stock item added successfully!",
                true,
                java.time.LocalDateTime.now().toString()
        );
    }
    public List<StockModel> getAllStocksByUser(Long userId) {
        return repository.list("user.id", userId);
    }

    @Transactional
    public ErrorMessage deleteStockItem(Long stockId, Long userId) {
        long deleted = repository.delete("id = ?1 and user.id = ?2", stockId, userId);
        if (deleted > 0) {
            return new ErrorMessage(
                    "Stock item with id " + stockId + " deleted successfully.",
                    true,
                    java.time.LocalDateTime.now().toString()
            );
        } else {
            return new ErrorMessage(
                    "Stock item not found or you are not authorized to delete it.",
                    false,
                    java.time.LocalDateTime.now().toString()
            );
        }
    }
}
