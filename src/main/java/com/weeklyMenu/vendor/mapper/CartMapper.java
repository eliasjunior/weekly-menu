package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.vendor.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDto cartToDto(Cart cart);

    List<CartDto> cartsToCartDtos(List<Cart> carts);

    Cart dtoToCart(CartDto dto);
}
