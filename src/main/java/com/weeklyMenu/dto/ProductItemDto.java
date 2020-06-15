package com.weeklyMenu.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductItemDto {
    private String id;
    private String prodId;
    private List<RecipeDto> recipes;
    boolean selected;
}
