package com.weeklyMenu.controllers;

import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.repository.CategoryRepository;

public class TestHelper {
    public static Product createProduct(Category category) {
        Product product = new Product();
        product.setId("01");
        product.setName("Chuck");
        product.setQuantityType("u");
        product.setCategory(category);
        return product;
    }

    public static Category createCategory(CategoryRepository repository) {
        Category category = new Category();
        category.setId("cat_01");
        return repository.save(category);
    }
}
