package com.weeklyMenu.adaptor.springData;

import com.weeklyMenu.adaptor.model.RecipeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<RecipeDB, String> {
    RecipeDB findByName(String name);

    @Query("SELECT c from RecipeDB c where UPPER(c.name) LIKE UPPER(?1)")
    RecipeDB findByNameIgnoreCase(String name);

    @Query("SELECT c from RecipeDB c where UPPER(c.name) LIKE UPPER(?1) AND c.id <> ?2")
    RecipeDB findByNameIgnoreCaseAndIdIsDiff(String name, String id);
}
