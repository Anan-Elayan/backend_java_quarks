package asd.inventory.controllers;

import asd.inventory.utils.constants.SystemPath;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


@Path(SystemPath.INVENTORY_MANAGEMENT)
public class InventoryController {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String inventoryMessage() {
        return "Welcome to the Inventory Management API!";
    }
}
