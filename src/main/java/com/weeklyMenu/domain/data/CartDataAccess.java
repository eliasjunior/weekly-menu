package com.weeklyMenu.domain.data;

import com.weeklyMenu.dto.CartDto;

import java.util.List;

public interface CartDataAccess {
    List<CartDto> getCartList();

    CartDto save(CartDto shoppingList);

    CartDto update(CartDto dto);

    void delete(String id);
}
