package com.weeklyMenu.useCase.Interactor.recipe;

import com.weeklyMenu.useCase.Interactor.validator.RecipeValidator;
import com.weeklyMenu.useCase.entity.Recipe;
import com.weeklyMenu.useCase.exceptions.CustomValidationException;
import com.weeklyMenu.useCase.gateway.RecipeGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;

import java.util.Optional;

import static java.util.Objects.isNull;

public class ManageRecipe {
    private final RecipeGateway recipeGateway;
    private final RecipeValidator recipeValidator;
    private final IdGenerator idGenerator;

    public ManageRecipe(RecipeGateway recipeGateway, RecipeValidator recipeValidator, IdGenerator idGenerator) {
        this.recipeGateway = recipeGateway;
        this.recipeValidator = recipeValidator;
        this.idGenerator = idGenerator;
    }

    public Recipe create(Recipe recipe) {
        recipeValidator.validateRecipe(recipe);
        recipeValidator.validateRecipeWithProducts(recipe);
        recipe.setId(idGenerator.generateId());
        return recipeGateway.create(recipe);
    }

    public void edit(Recipe recipe) {
        recipeValidator.validateRecipe(recipe);
        Optional<Recipe> optional = recipeGateway.findById(recipe.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Recipe not found to update");
        }
        recipeValidator.validateRecipeWithProducts(recipe);
        generateItemsIds(idGenerator, recipe);

        recipeGateway.edit(recipe);
    }

    public void remove(String id) {
        recipeGateway.remove(id);
    }


    private void generateItemsIds(IdGenerator idGenerator, Recipe recipe) {
        recipe.getProdsDetail().forEach(prodDetail -> {
            if (isNull(prodDetail.getId()) || prodDetail.getId().isEmpty()) {
                //TODO need a test for regression here
                //TODO if its update and there is not ID means could have a new prod checked
                prodDetail.setId(idGenerator.generateId());
            }
        });
    }
}
