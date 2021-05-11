package com.weeklyMenu.controllers;

import com.weeklyMenu.webAdaptor.model.CategoryDB;
import com.weeklyMenu.webAdaptor.model.ProductDB;
import com.weeklyMenu.webAdaptor.repository.CategoryRepository;

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
