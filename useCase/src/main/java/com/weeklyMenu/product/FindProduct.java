package main.java.com.weeklyMenu.product;

import main.java.com.weeklyMenu.entity.Product;

import java.util.List;

public interface FindProduct {
    List<Product> getAllProducts();
    Product getProduct(String id);
    boolean isProductNameUsed(Product dto);
}
