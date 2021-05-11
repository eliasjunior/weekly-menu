package com.weeklyMenu.webAdaptor.mapper;

import com.weeklyMenu.webAdaptor.model.CartDB;
import main.java.com.weeklyMenu.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart cartDBToCart(CartDB cart);

    List<Cart> cartsDBToCarts(List<CartDB> carts);

    CartDB cartToCartDB(Cart dto);
}
