package com.weeklyMenu.vendor.controller;


import com.weeklyMenu.domain.data.CartAccessData;
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
@RequestMapping(GlobalConstant.BASE_URL + "/cart")
public class CartController {
    final CartAccessData cartAccessData;
    final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartController(CartAccessData cartAccessData) {
        this.cartAccessData = cartAccessData;
    }

    @GetMapping
    public List<CartDto> getCartList() {
        LOGGER.info("--> getCartList");
        return cartAccessData.getCartList();
    }

    @PostMapping
    public CartDto postShoppingList(@RequestBody CartDto dto) {
        LOGGER.info("--> save", dto);
        return cartAccessData.save(dto);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody CartDto dto) {
        LOGGER.info("--> update", dto);
        cartAccessData.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        cartAccessData.delete(id);
        return ResponseEntity.noContent().build();
    }
}
