package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.ProductItemDto;
import com.weeklyMenu.vendor.model.ProductItem;
import com.weeklyMenu.vendor.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDto cartToDto(Cart cart);
    @Mapping(source = "product.id", target = "prodId")
    ProductItemDto productItemToItemDto(ProductItem productItem);

    Cart dtoToCart(CartDto dto);
    @Mapping(source = "prodId", target = "product.id")
    ProductItem dtoItemToProductItem(ProductItemDto dtoItem);

    List<CartDto> cartToDto(List<Cart> cart);
}
