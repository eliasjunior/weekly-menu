package com.weeklyMenu.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    public CategoryDto() {
    }
    public CategoryDto(String id) {
        this.id = id;
    }

    private String id;
    private String name;
    private List<ProductDto> products;
    private List<String> prodIds;
}
