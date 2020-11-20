package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByName(String name);

    @Query("SELECT c from Category c where UPPER(c.name) LIKE UPPER(?1)")
    Category findByNameIgnoreCase(String name);

    @Query("SELECT c from Category c where UPPER(c.name) LIKE UPPER(?1) AND c.id <> ?2")
    Category findByNameIgnoreCaseAndIdIsDiff(String name, String id);
}

