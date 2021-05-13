package com.weeklyMenu.adaptor.controller.model;

import lombok.Data;
import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.entity.Product;

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
