package com.weeklyMenu.controllers;

import com.weeklyMenu.vendor.model.CategoryDB;
import com.weeklyMenu.vendor.model.ProductDB;
import com.weeklyMenu.vendor.repository.CategoryRepository;

public class TestHelper {
    public static ProductDB createProduct(CategoryDB category) {
        ProductDB product = new ProductDB();
        product.setId("01");
        product.setName("Chuck");
        product.setQuantityType("u");
        product.setCategory(category);
        return product;
    }

    public static CategoryDB createCategory(CategoryRepository repository) {
        CategoryDB category = new CategoryDB();
        category.setId("cat_01");
        category.setName("Seeds");
        return repository.save(category);
    }
}
