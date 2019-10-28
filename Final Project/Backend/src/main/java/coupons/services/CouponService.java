package coupons.services;

import coupons.dtos.PageInfo;
import coupons.mappers.CouponMapper;
import coupons.models.Coupon;
import coupons.dtos.CouponDTO;
import coupons.repos.CouponRepository;
import coupons.responses.ObjectResponse;

import java.util.ArrayList;
import java.util.List;

public class CouponService {

    private CouponRepository couponRepository = new CouponRepository();

    public ObjectResponse<CouponDTO> get(int page, boolean active) {
        List<CouponDTO> dtos = new ArrayList<>();
        List<Coupon> coupons = couponRepository.get(page, active);

        for (Coupon c : coupons) {
            dtos.add(CouponMapper.instance.map(c));
        }

        long total = couponRepository.count(active);
        long pages = (long)Math.ceil(total/20.);

        return new ObjectResponse<>(dtos, new PageInfo(total, pages, page));
    }

    public long deleteShop(String shopId) {
        return couponRepository.deleteShop(shopId);
    }

    public long delete(String id) {
        return couponRepository.delete(id);
    }

    public long count(boolean active) {
        return couponRepository.count(active);
    }

//    public CouponDTO create() {
//
//    }
}
