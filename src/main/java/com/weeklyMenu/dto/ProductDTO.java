package com.weeklyMenu.dto;

import lombok.Data;

@Data
public class ProductDto {
    public ProductDto() {}
    public ProductDto(String id) {
        this.id = id;
    }
    private String id;
    private String name;
    private String quantityType;
    private String catId;
}