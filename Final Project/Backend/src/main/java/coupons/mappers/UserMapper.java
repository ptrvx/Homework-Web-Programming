package coupons.mappers;

import coupons.models.User;
import coupons.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper instance = Mappers.getMapper(UserMapper.class);

    UserDTO map(User user);
}
