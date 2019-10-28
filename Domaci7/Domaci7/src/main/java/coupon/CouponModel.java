package coupon;

public class CouponModel {
	
	private String id;
	private String shopId;
	private String shopName;
	private String product;
	private float discountedPrice;
	private float originalPrice;
	private float sale;
	
	
	public CouponModel(String shop, String product, float dPrice, float oPrice) {
		this.shopId = shop;
		this.product = product;
		this.discountedPrice = dPrice;
		this.originalPrice = oPrice;
	}
	
	public CouponModel(Coupon coupon) {
		this.id = coupon.getId();
		this.shopId = coupon.getShop().getId();
		this.shopName = coupon.getShop().getName();
		this.product = coupon.getProduct();
		this.discountedPrice = coupon.getDiscountedPrice();
		this.originalPrice = coupon.getOriginalPrice();
		this.sale = (originalPrice-discountedPrice) / originalPrice * 100;
	}
	
	
	
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public float getSale() {
		return sale;
	}
	public void setSale(float sale) {
		this.sale = sale;
	}
	
	
}
