package main.java.com.weeklyMenu.Interactor.cart;

import main.java.com.weeklyMenu.entity.Cart;
import main.java.com.weeklyMenu.gateway.CartGateway;

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
