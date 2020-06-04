package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByName(String name);
}

