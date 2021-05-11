package main.java.com.weeklyMenu.Interactor.recipe;

import main.java.com.weeklyMenu.entity.Recipe;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.RecipeGateway;

import java.util.List;
import java.util.Optional;

public class FindRecipe {
    private final RecipeGateway recipeGateway;

    public FindRecipe(RecipeGateway recipeGateway) {
        this.recipeGateway = recipeGateway;
    }

    public List<Recipe> getAllRecipes() {
        return this.recipeGateway.getAllRecipes();
    }


    public Recipe getRecipe(String id) {
        Optional<Recipe> optional = recipeGateway.findById(id);
        if(!optional.isPresent()) {
            throw new CustomValidationException("Attempt to load recipe has failed");
        }
        return optional.get();
    }
}
