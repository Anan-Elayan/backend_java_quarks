package asd.inventory.controllers;

import asd.inventory.models.StockModel;
import asd.inventory.services.StockServices;
import asd.inventory.utils.constants.SystemPath;
import asd.inventory.utils.functionality.ErrorMessage;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path(SystemPath.INVENTORY_MANAGEMENT + SystemPath.STOCKS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StockController {

    @Inject
    StockServices stockServices;

    @GET
    @Path(SystemPath.ALL_STOCKS)
    public List<StockModel> getStocks() {
        return stockServices.getAllStocks();

    }

    @POST
    @Path(SystemPath.ADD_STOCK)
    public Response addStockItem(StockModel stockModel) {
        ErrorMessage successMessage = stockServices.addStockItem(stockModel);
        return Response.status(Response.Status.OK)
                .entity(successMessage)
                .build();
    }

    @DELETE
    @Path(SystemPath.DELETE + "/{id}")
    public Response deleteStockItem(@PathParam("id") Long id) {
        ErrorMessage successMessage = stockServices.deleteStockItem(id);
        return Response.status(Response.Status.OK)
                .entity(successMessage)
                .build();
    }

    private ContainerRequestContext requestContext;

    @GET
    @Path("/all")
    public Object getAllStocks() {
        Object startTime = requestContext.getProperty("requestStartTime");
        System.out.println("Request start time in controller: " + startTime);
        return stockServices.getAllStocks();
    }

}
