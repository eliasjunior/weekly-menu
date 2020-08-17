package com.weeklyMenu;

import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;

import java.util.UUID;

public class ProductFactory {
    public static Product createProduct(String name, Category category) {
        Product product = new Product();
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
