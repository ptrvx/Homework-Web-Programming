package coupons.mappers;

import coupons.models.Shop;
import coupons.dtos.ShopDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {
    ShopMapper instance = Mappers.getMapper(ShopMapper.class);

    ShopDTO map(Shop shop);
}
