package com.weeklyMenu.useCase.Interactor.cart;

import com.weeklyMenu.useCase.entity.Cart;
import com.weeklyMenu.useCase.gateway.CartGateway;

import java.util.List;

public class FindCart {
    private final CartGateway cartGateway;

    public FindCart(CartGateway cartGateway) {
        this.cartGateway = cartGateway;
    }

    public List<Cart> getCartList() {
        return this.cartGateway.getCartList();
    }
}
