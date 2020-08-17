package com.weeklyMenu.dto;

import lombok.Data;

import java.util.List;

//TODO ProductItemDto should be CartItem
@Data
public class CartItemDto {
    private String id;
    private String prodId;
    private List<RecipeDto> recipes;
    boolean selected;
}
