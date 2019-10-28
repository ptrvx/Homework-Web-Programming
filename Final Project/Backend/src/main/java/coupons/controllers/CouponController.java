package coupons.controllers;

import coupons.binding.Secured;
import coupons.dtos.CouponDTO;
import coupons.enums.Privilege;
import coupons.responses.ObjectResponse;
import coupons.services.CouponService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/coupons")
public class CouponController {

    private CouponService couponService = new CouponService();

    @GET
    @Secured({Privilege.ADMINISTRATOR, Privilege.OPERATOR})
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectResponse<CouponDTO> get(@QueryParam("page")@DefaultValue("1") int page, @QueryParam("active")@DefaultValue("false") boolean active) {
        return couponService.get(page, active);
    }

    @DELETE
    @Path("/{couponId}")
    @Secured({Privilege.ADMINISTRATOR, Privilege.OPERATOR})
    @Produces(MediaType.APPLICATION_JSON)
    public long get(@PathParam("couponId") String id) {
        return couponService.delete(id);
    }

//    @POST
//    @Secured({Privilege.ADMINISTRATOR, Privilege.OPERATOR})
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public CouponDTO create() {
//
//    }


}
