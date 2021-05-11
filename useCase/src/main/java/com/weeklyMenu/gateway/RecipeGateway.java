package main.java.com.weeklyMenu.gateway;

import main.java.com.weeklyMenu.entity.Recipe;

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
