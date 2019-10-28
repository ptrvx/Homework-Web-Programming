package coupon;

import java.io.Serializable;
import java.util.UUID;

public class Coupon implements Serializable {
	
	
	private String id;
	private Shop shop;
	private String product;
	private float discountedPrice;
	private float originalPrice;
	

	public Coupon(Shop shop, String product, float originalPrice, float discountedPrice) {
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
		this.shop = shop;
		this.product = product;
		this.discountedPrice = discountedPrice;
		this.originalPrice = originalPrice;
	}
	
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public float getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(float discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	
	@Override
	public boolean equals(Object obj) {	
        if (obj == this) { 
            return true; 
        } 
  
        if (!(obj instanceof Coupon)) { 
            return false; 
        } 
          
        Coupon c = (Coupon) obj; 
          
        return this.id.equals(c.getId());
	}

	
	
}
