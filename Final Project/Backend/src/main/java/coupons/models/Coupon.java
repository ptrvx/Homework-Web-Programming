package coupons.models;

import java.util.Date;

public class Coupon {

    private String id;
    private Shop shop;
    private String product;
    private float discountedPrice;
    private float originalPrice;
    private Date validFrom;
    private Date validTo;

    public Coupon(){}

    public Coupon(String id, Shop shop, String product, float discountedPrice, float originalPrice, Date validFrom, Date validTo) {
        this.id = id;
        this.shop = shop;
        this.product = product;
        this.discountedPrice = discountedPrice;
        this.originalPrice = originalPrice;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public Coupon(String id, Shop shop, String product, float discountedPrice, float originalPrice, Date validFrom) {
        this.id = id;
        this.shop = shop;
        this.product = product;
        this.discountedPrice = discountedPrice;
        this.originalPrice = originalPrice;
        this.validFrom = validFrom;
        this.validTo = null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setDiscountedPrice(float discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getId() {
        return id;
    }

    public Shop getShop() {
        return shop;
    }

    public String getProduct() {
        return product;
    }

    public float getDiscountedPrice() {
        return discountedPrice;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }
}
