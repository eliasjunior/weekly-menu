package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.model.Cart;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDto cartToDto(Cart cart);
    List<CartDto> cartsToCartDtos(List<Cart> carts);
    @Mapping(source = "product.id", target = "prodId")
    CartItemDto cartItemToCartItemDto(CartItem cartItem);
    List<CartItemDto> cartItemsToCartItemsDtos(List<CartItem> itemDtos);

    Cart dtoToCart(CartDto dto);
    List<Cart> cartDtosToCarts(List<CartDto> cartDtos);
    @Mapping(source = "prodId", target = "product.id")
    CartItem cartItemDtoToCartItem(CartItemDto dtoItem);
    List<CartItem> cartItemsDtosToCartItems(List<CartItemDto> itemDtos);



}
