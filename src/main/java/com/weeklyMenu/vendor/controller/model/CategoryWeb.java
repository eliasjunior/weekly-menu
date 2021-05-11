package com.weeklyMenu.vendor.controller.model;

import lombok.Data;
import main.java.com.weeklyMenu.entity.Category;
import main.java.com.weeklyMenu.entity.Product;

import java.util.List;

@Data
public class CategoryWeb {
    private String id;
    private String name;
    private List<Product> products;
    private List<String> prodIds;

    public Category toCategory() {
        return Category.builder()
                .id(id)
                .name(name)
                .products(products)
                .prodIds(prodIds)
                .build();
    }

    public static CategoryWeb toCategoryWeb(Category category) {
        var catWeb = new CategoryWeb();
        catWeb.setId(category.getId());
        catWeb.setName(category.getName());
        return catWeb;
    }
}
