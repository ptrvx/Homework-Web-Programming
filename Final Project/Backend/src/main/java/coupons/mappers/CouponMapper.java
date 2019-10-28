package coupons.mappers;

import coupons.models.Coupon;
import coupons.dtos.CouponDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CouponMapper {
    CouponMapper instance = Mappers.getMapper(CouponMapper.class);

    CouponDTO map(Coupon coupon);
}
