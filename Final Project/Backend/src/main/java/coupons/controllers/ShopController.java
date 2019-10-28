package coupons.controllers;

import coupons.binding.Secured;
import coupons.dtos.CouponDTO;
import coupons.dtos.ShopDTO;
import coupons.enums.Privilege;
import coupons.responses.ObjectResponse;
import coupons.services.ShopService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/shops")
public class ShopController {

    private ShopService shopService = new ShopService();

    @GET
    @Secured({Privilege.ADMINISTRATOR, Privilege.OPERATOR})
    @Produces(MediaType.APPLICATION_JSON)
    public List<ShopDTO> get() {
        return shopService.get();
    }

    @GET
    @Path("/{shopId}")
    @Secured({Privilege.ADMINISTRATOR, Privilege.OPERATOR})
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectResponse<CouponDTO> find(@PathParam("shopId") String shopId) {
        return this.shopService.find(shopId);
    }

    @DELETE
    @Path("/{shopId}")
    @Secured({Privilege.ADMINISTRATOR, Privilege.OPERATOR})
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("shopId") String shopId) {
        shopService.delete(shopId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


    @POST
    @Secured({Privilege.ADMINISTRATOR, Privilege.OPERATOR})
    @Produces(MediaType.APPLICATION_JSON)
    public ShopDTO create(@QueryParam("name") String name) {
        return shopService.create(name);
    }



}
