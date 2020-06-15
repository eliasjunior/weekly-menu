package com.weeklyMenu.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {
    private String id;
    private String name;
    private List<ProdDetailDto> prodsDetail;
}
