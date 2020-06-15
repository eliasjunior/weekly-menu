package com.weeklyMenu.dto;

import lombok.Data;

@Data
public class ProductDTO {
    public ProductDTO() {}
    public ProductDTO(String id) {
        this.id = id;
    }
    private String id;
    private String name;
    private String quantityType;
    private String catId;
}