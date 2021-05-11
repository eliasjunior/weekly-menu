package com.weeklyMenu.vendor.controller;


import main.java.com.weeklyMenu.cart.FindCart;
import main.java.com.weeklyMenu.cart.ManageCart;
import main.java.com.weeklyMenu.common.GlobalConstant;
import main.java.com.weeklyMenu.useCase.data.CartDataAccess;
import com.weeklyMenu.dto.Cart;
import com.weeklyMenu.helpers.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return cartDataAccess.getCartList();
    }

    @PostMapping
    public Cart postShoppingList(@RequestBody Cart dto) {
        LOGGER.info("--> save", dto);
        return cartDataAccess.save(dto);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Cart dto) {
        LOGGER.info("--> update", dto);
        cartDataAccess.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        cartDataAccess.delete(id);
        return ResponseEntity.noContent().build();
    }
}
