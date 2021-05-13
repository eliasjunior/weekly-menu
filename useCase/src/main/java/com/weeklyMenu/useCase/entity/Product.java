package com.weeklyMenu.useCase.entity;

import lombok.Data;

@Data
public class Product {
    public Product() {}
    public Product(String id) {
        this.id = id;
    }
    private String id;
    private String name;
    private String quantityType;
    private String catId;
}
