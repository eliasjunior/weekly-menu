package com.weeklyMenu;

import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.vendor.model.CategoryDB;
import com.weeklyMenu.vendor.model.ProductDB;

import java.util.UUID;

public class ProductFactory {
    public static ProductDB createProduct(String name, CategoryDB category) {
        ProductDB product = new ProductDB();
        product.setName(name);
        product.setCategory(category);
        product.setId(UUID.randomUUID().toString());
        return product;
    }

    public static ProductDto createProductDto(String name,String catId) {
        ProductDto product = new ProductDto();
        product.setName(name);
        product.setCatId(catId);
        product.setId(UUID.randomUUID().toString());
        return product;
    }
}
