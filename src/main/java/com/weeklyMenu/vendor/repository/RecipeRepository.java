package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
    Recipe findByName(String name);

    @Query("SELECT c from Recipe c where UPPER(c.name) LIKE UPPER(?1)")
    Recipe findByNameIgnoreCase(String name);

    @Query("SELECT c from Recipe c where UPPER(c.name) LIKE UPPER(?1) AND c.id <> ?2")
    Recipe findByNameIgnoreCaseAndIdIsDiff(String name, String id);
}
