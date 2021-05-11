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
        return null;
//        var catWeb = new CategoryWeb();
//        catWeb.set(user.getId());
//        userWeb.setEmail(user.getEmail());
//        // do not map password
//        userWeb.setLastName(user.getLastName());
//        userWeb.setFirstName(user.getFirstName());
//        return userWeb;
    }
}
