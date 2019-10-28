package coupon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CouponRepository {

	
	private static String[] SHOP_NAME_LIST = {"Gozzby", "Doniry", "Dofort", "Vorofy", "Woniry", "Fonicy", "Wexmon", "Rumpes", "Wopno", "Vravmo", "Rynaty", "Mexmon", "Lebami", "Clerby"};
	
	private static List<Shop> SHOP_LIST;
	
	private static List<Coupon> COUPON_LIST = new ArrayList<Coupon>();
	
	static {
		SHOP_LIST = generateShops();
		
		COUPON_LIST.add(new Coupon(SHOP_LIST.get(2), "Jabuke", 55, 26));
		COUPON_LIST.add(new Coupon(SHOP_LIST.get(1), "Lubenice", 52, 29));
		COUPON_LIST.add(new Coupon(SHOP_LIST.get(3), "Banane", 53, 21));
		
	}
	
	private static List<Shop> generateShops() {
		List<Shop> shops = new ArrayList<Shop>();
		
		for (int i=0; i < SHOP_NAME_LIST.length; i++) {
			
			Shop shop = new Shop();
			shop.setId(Integer.toString(i));
			shop.setName(SHOP_NAME_LIST[i]);
			
			shops.add(shop);
		}
		return shops;		
	}
	
	
	public synchronized static List<Shop> getShops() {
		return SHOP_LIST;
	}
	
	public synchronized static List<Coupon> getCoupons() {
		return COUPON_LIST;
	}
	
	public synchronized static Coupon addCoupon(CouponModel cModel) {
		UUID uuid = UUID.randomUUID();
		Shop shop = null;
		
		for (Shop s : SHOP_LIST) {
			if (s.getId().equals(cModel.getShopId())) {
				shop = s;
			}
		}
		
		if (shop != null) {
			Coupon coupon = new Coupon(shop, cModel.getProduct(), cModel.getOriginalPrice(), cModel.getDiscountedPrice());
				
			coupon.setId(uuid.toString());
			COUPON_LIST.add(coupon);
			
			return coupon;
		} else {
			return null;
		}
		
	}
	
	public synchronized static boolean removeCoupon(String id) {
		for (int i=0; i < COUPON_LIST.size(); i++) {
			if (COUPON_LIST.get(i).getId().toString().equals(id)) {
				COUPON_LIST.remove(i);
				return true;
			}
		}	
		return false;
	}
	

	public synchronized static boolean addCoupon() {
		COUPON_LIST.add(new Coupon(SHOP_LIST.get(3), "Banane", 53, 21));
		return true;
	}
	
}
