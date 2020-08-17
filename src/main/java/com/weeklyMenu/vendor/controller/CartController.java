package com.weeklyMenu.vendor.controller;


import com.weeklyMenu.domain.data.CartDataAccess;
import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.helpers.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    final CartDataAccess cartDataAccess;
    final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartController(CartDataAccess cartDataAccess) {
        this.cartDataAccess = cartDataAccess;
    }

    @GetMapping
    public List<CartDto> getCartList() {
        LOGGER.info("--> getCartList");
        return cartDataAccess.getCartList();
    }

    @PostMapping
    public CartDto postShoppingList(@RequestBody CartDto dto) {
        LOGGER.info("--> save", dto);
        return cartDataAccess.save(dto);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody CartDto dto) {
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
