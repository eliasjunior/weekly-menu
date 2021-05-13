package com.weeklyMenu.useCase.entity;

import lombok.Data;
import java.util.List;
@Data
public class Cart {
    private String id;
    private String name;
    private List<CartItem> cartItems;
}
