package com.weeklyMenu.useCase.gateway;

import com.weeklyMenu.useCase.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {
    Optional<Product> findById(String id);
    Product create(Product product);
    Product findByNameIgnoreCase(String name);
    Product findByNameIgnoreCaseAndIdIsDiff(String name, String id);
    void edit(Product product);
    void remove(String id);
    Product getProduct(String id);
    Product findByName(String name);
    List<Product> getAllProducts();
    //List<Product> findAll();
}
