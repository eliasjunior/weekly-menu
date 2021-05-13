package com.weeklyMenu.adaptor.controller;

import com.weeklyMenu.useCase.Interactor.cart.FindCart;
import com.weeklyMenu.useCase.Interactor.cart.ManageCart;
import com.weeklyMenu.useCase.common.GlobalConstant;
import com.weeklyMenu.useCase.entity.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalConstant.BASE_URL + "/carts")
public class CartController {
    final FindCart findCart;
    final ManageCart manageCart;
    final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    public CartController(FindCart findCart, ManageCart manageCart) {
        this.findCart = findCart;
        this.manageCart = manageCart;
    }

    @GetMapping
    public List<Cart> getCartList() {
        LOGGER.info("--> getCartList");
        return findCart.getCartList();
    }

    @PostMapping
    public Cart postShoppingList(@RequestBody Cart cart) {
        LOGGER.info("--> save " + cart);
        return manageCart.create(cart);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Cart cart) {
        LOGGER.info("--> update " + cart);
        manageCart.edit(cart);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        manageCart.remove(id);
        return ResponseEntity.noContent().build();
    }
}
