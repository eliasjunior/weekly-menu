package com.weeklyMenu.domain.data;

import com.weeklyMenu.dto.CartDto;

import java.util.List;

public interface CartAccessData {
    List<CartDto> getCartList();

    CartDto save(CartDto shoppingList);

    void update(CartDto dto);

    void delete(String id);
}
