package com.weeklyMenu.inventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {
    private String id;
    private String name;
    private List<ProdDetailDto> prodsDetail;
}
