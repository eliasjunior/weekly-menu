package com.weeklyMenu.inventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private String id;
    private String name;
    private List<ProductDTO> products;
}
