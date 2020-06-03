package com.weeklyMenu.inventory.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String id;
    private String catId;
    private String name;
    private String quantityType;
}