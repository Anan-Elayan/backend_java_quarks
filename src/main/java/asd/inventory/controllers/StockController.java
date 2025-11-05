package asd.inventory.controllers;

import asd.inventory.models.StockModel;
import asd.inventory.models.UserModel;
import asd.inventory.services.StockServices;
import asd.inventory.utils.constants.SystemPath;
import asd.inventory.utils.functionality.ErrorMessage;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.List;
import java.util.Map;

@Path(SystemPath.INVENTORY_MANAGEMENT + SystemPath.STOCKS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StockController {

    @Inject
    StockServices stockServices;

    @Inject
    jakarta.ws.rs.core.HttpHeaders headers;

    @GET
    @Path(SystemPath.ALL_STOCKS)
    @RolesAllowed({"User"})
    public List<StockModel> getStocks(@Context ContainerRequestContext requestContext) {
        Claims claims = (Claims) requestContext.getProperty("claims");
        Long userId = ((Number)((Map<?,?>)claims.get("user")).get("id")).longValue();
        return stockServices.getAllStocksByUser(userId);
    }


    @POST
    @Path(SystemPath.ADD_STOCK)
    @RolesAllowed({"User"})
    public ErrorMessage addStock(@Context ContainerRequestContext requestContext, StockModel stockModel) {
        Claims claims = (Claims) requestContext.getProperty("claims");
        Long userId = ((Number)((Map<?,?>)claims.get("user")).get("id")).longValue();
        String userName = (String) claims.getSubject();

        // إنشاء كائن UserModel مختصر فقط لإرسالها للـ service
        UserModel user = new UserModel();
        user.setId(userId);
        user.setName(userName);

        return stockServices.addStockItem(stockModel, user);
    }

    @DELETE
    @Path(SystemPath.DELETE + "/{id}")
    @RolesAllowed({"User"})
    public ErrorMessage deleteStock(@Context ContainerRequestContext requestContext, @PathParam("id") Long stockId) {
        Claims claims = (Claims) requestContext.getProperty("claims");
        Long userId = ((Number)((Map<?,?>)claims.get("user")).get("id")).longValue();
        return stockServices.deleteStockItem(stockId, userId);
    }




}
