package coupon;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/coupons")
public class CouponController {
	
	private final CouponService couponService;
	
	public CouponController() {
		this.couponService = new CouponService();
	}
	
	@GET
	@Path("/test")
	public String test() {
		return "REST is working.";
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CouponModel> getUsers() {
        return couponService.getCoupons();
    }
	
	// Mogao bi da se pomeri da ne bude /coupons/shops
	@GET
	@Path("/shops")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Shop> getShops() {
		return couponService.getShops();
	}
	
	@DELETE
    @Path("/{id}")
    public boolean getUserById(@PathParam("id") String id) {
        return couponService.deleteCoupon(id);
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public CouponModel addCoupon(@FormParam("shopName") String shop, @FormParam("product") String product, @FormParam("discountedPrice") float discountedPrice, @FormParam("originalPrice") float originalPrice) {
		return couponService.addCoupon(new CouponModel(shop, product, discountedPrice, originalPrice));
	}
	
	@PUT
	public boolean addPlaceholderCoupon() {
		return couponService.addPlaceholderCoupon();
	}
	
}
