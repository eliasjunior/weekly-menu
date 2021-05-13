package com.weeklyMenu.useCase.Interactor.recipe;

import com.weeklyMenu.useCase.entity.Recipe;
import com.weeklyMenu.useCase.exceptions.CustomValidationException;
import com.weeklyMenu.useCase.gateway.RecipeGateway;

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
        if(optional.isEmpty()) {
            throw new CustomValidationException("Attempt to load recipe has failed");
        }
        return optional.get();
    }

    public boolean isRecipeNameUsed(Recipe dto) {
        Recipe recipe = recipeGateway.findByName(dto.getName());
        return recipe != null;
    }
}
