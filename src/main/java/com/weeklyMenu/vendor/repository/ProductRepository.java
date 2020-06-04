package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByName(String name);
}