package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
    Recipe findByName(String name);
}
