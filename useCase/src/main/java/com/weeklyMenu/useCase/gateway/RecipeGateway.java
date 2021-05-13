package com.weeklyMenu.useCase.gateway;

import com.weeklyMenu.useCase.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeGateway {
    Optional<Recipe> findById(String id);
    List<Recipe> getAllRecipes();
    Recipe create(Recipe recipe);
    void edit(Recipe dto);
    void remove(String id);
    Recipe findByNameIgnoreCase(String name);
    Recipe findByNameIgnoreCaseAndIdIsDiff(String name, String id);
    Recipe findByName(String name);
}
