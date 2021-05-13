package com.weeklyMenu.useCase.gateway;

import com.weeklyMenu.useCase.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartGateway {
    Cart create(Cart cart);
    void edit(Cart cart);
    void remove(String id);
    List<Cart> getCartList();
    Optional<Cart> findById(String id);
}
