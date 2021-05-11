package main.java.com.weeklyMenu.gateway;

import main.java.com.weeklyMenu.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartGateway {
    Cart create(Cart cart);
    void edit(Cart cart);
    void remove(String id);
    List<Cart> getCartList();
    Optional<Cart> findById(String id);
}
