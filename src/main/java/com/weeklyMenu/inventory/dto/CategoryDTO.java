package com.weeklyMenu.inventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    public CategoryDTO() {
    }
    public CategoryDTO(String id) {
        this.id = id;
    }

    private String id;
    private String name;
    private List<ProductDTO> products;
    private List<String> catProds;
}
