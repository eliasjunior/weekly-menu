package com.weeklyMenu.useCase.entity;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class Cart {
    private String id;
    private String name;
    private List<CartItem> cartItems;
}
