package com.weeklyMenu.dto;

import lombok.Data;

import java.util.List;
@Data
public class CartDto {
    private String id;
    private String name;
    private List<ProductItemDto> productItems;
}
