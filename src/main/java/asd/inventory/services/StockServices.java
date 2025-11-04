package asd.inventory.services;

import asd.inventory.repositories.StocksRepository;
import asd.inventory.models.StockModel;
import asd.inventory.utils.functionality.ErrorMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.List;

@ApplicationScoped
public class StockServices {

    @Inject
    StocksRepository repository;
    private ContainerRequestContext requestContext;


    @Transactional
    public ErrorMessage addStockItem(StockModel stockModel) {
        repository.addStockItem(stockModel);
        return new ErrorMessage(
                "Stock item added successfully!",
                true,
                java.time.LocalDateTime.now().toString()
        );
    }

    public List<StockModel> getAllStocks() {
        return repository.getAllStocks();
    }

    @Transactional
    public ErrorMessage deleteStockItem(Long id) {
        repository.deleteStockItem(id);
        return new ErrorMessage(
                "Stock item with id " + id + " deleted successfully.",
                true,
                java.time.LocalDateTime.now().toString()
        );
    }



    public void someServiceMethod() {
        Object startTime = requestContext.getProperty("requestStartTime");
        System.out.println("Request start time in service: " + startTime);
    }
}
