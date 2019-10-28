package coupons.dtos;

import java.util.Date;

public class CouponDTO {

    private String id;
    private ShopDTO shop;
    private String product;
    private float discountedPrice;
    private float originalPrice;
    private Date validFrom;
    private Date validTo;
    private float sale;

    public float getSale() {
        return (originalPrice - discountedPrice) / originalPrice * 100;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShop(ShopDTO shop) {
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

    public ShopDTO getShop() {
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
