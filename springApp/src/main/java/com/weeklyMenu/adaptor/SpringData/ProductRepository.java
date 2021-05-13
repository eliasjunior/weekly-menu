package com.weeklyMenu.adaptor.SpringData;

import com.weeklyMenu.adaptor.model.ProductDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<ProductDB, String> {
    ProductDB findByName(String name);

    @Query("SELECT p from ProductDB p where UPPER(p.name) LIKE UPPER(?1) and UPPER(p.basicEntity.status) = 'A'")
    ProductDB findByNameIgnoreCase(String name);

    @Query("SELECT p from ProductDB p where UPPER(p.name) LIKE UPPER(?1) AND p.id <> ?2 and UPPER(p.basicEntity.status) = 'A'")
    ProductDB findByNameIgnoreCaseAndIdIsDiff(String name, String id);
}