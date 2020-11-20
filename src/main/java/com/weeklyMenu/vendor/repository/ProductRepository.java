package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByName(String name);

    @Query("SELECT p from Product p where UPPER(p.name) LIKE UPPER(?1) and UPPER(p.basicEntity.status) = 'A'")
    Product findByNameIgnoreCase(String name);

    @Query("SELECT p from Product p where UPPER(p.name) LIKE UPPER(?1) AND p.id <> ?2 and UPPER(p.basicEntity.status) = 'A'")
    Product findByNameIgnoreCaseAndIdIsDiff(String name, String id);
}