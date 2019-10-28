package coupons.services;

import coupons.dtos.CouponDTO;
import coupons.dtos.PageInfo;
import coupons.mappers.CouponMapper;
import coupons.mappers.ShopMapper;
import coupons.models.Coupon;
import coupons.models.Shop;
import coupons.dtos.ShopDTO;
import coupons.repos.CouponRepository;
import coupons.repos.ShopRepository;
import coupons.responses.ObjectResponse;

import java.util.ArrayList;
import java.util.List;

public class ShopService {

    private ShopRepository shopRepository = new ShopRepository();
    private CouponRepository couponRepository = new CouponRepository();

    public List<ShopDTO> get() {
        List<ShopDTO> dtos = new ArrayList<>();
        List<Shop> shops = shopRepository.get();
        for (Shop shop : shops) {
            dtos.add(ShopMapper.instance.map(shop));
        }

        return dtos;
    }

    public ObjectResponse<ShopDTO> get(int page) {
        List<ShopDTO> dtos = new ArrayList<>();
        List<Shop> shops = shopRepository.get(page);
        for (Shop shop : shops) {
            dtos.add(ShopMapper.instance.map(shop));
        }

        long total = shopRepository.total();
        long pages = (long)Math.ceil(total/20.);

        ObjectResponse<ShopDTO> response = new ObjectResponse<>(dtos, new PageInfo(total, pages, page));

        return response;
    }

    public ObjectResponse<CouponDTO> find(String id) {
        Shop shop = shopRepository.find(id);
        List<CouponDTO> dtos = new ArrayList<>();
        List<Coupon> coupons = couponRepository.get(id);
        for (Coupon coupon : coupons) {
            dtos.add(CouponMapper.instance.map(coupon));
        }

        return new ObjectResponse<>(dtos, ShopMapper.instance.map(shop));
    }

    public long delete(String shopId) {
        couponRepository.deleteShop(shopId);
        return shopRepository.delete(shopId);
    }

    public ShopDTO create(String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        return ShopMapper.instance.map(shopRepository.create(name));
    }

}
