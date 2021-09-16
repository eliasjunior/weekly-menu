package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.CartDB;
import com.weeklyMenu.useCase.entity.Cart;

import java.util.List;

public interface CartMapper {
    Cart cartDBToCart(CartDB cart);

    List<Cart> cartsDBToCarts(List<CartDB> carts);

    CartDB cartToCartDB(Cart dto);
}
