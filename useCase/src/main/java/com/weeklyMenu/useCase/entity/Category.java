package com.weeklyMenu.useCase.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Category {
    private String id;
    private String name;
    private List<Product> products;
    private List<String> prodIds;
}
