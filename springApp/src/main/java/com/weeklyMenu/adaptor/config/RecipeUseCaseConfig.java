package com.weeklyMenu.adaptor.config;

import com.weeklyMenu.useCase.Interactor.recipe.FindRecipe;
import com.weeklyMenu.useCase.Interactor.recipe.ManageRecipe;
import com.weeklyMenu.useCase.Interactor.validator.RecipeValidator;
import com.weeklyMenu.useCase.gateway.RecipeGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeUseCaseConfig  {
    private final RecipeGateway recipeGateway;
    private final RecipeValidator recipeValidator;
    private final IdGenerator idGenerator;

    public RecipeUseCaseConfig(RecipeGateway recipeGateway, RecipeValidator recipeValidator, IdGenerator idGenerator) {
        this.recipeGateway = recipeGateway;
        this.recipeValidator = recipeValidator;
        this.idGenerator = idGenerator;
    }

    @Bean
    public FindRecipe createFindRecipe() {
        return new FindRecipe(recipeGateway);
    }

    @Bean
    public ManageRecipe createManageRecipe() {
        return new ManageRecipe(recipeGateway, recipeValidator, idGenerator);
    }
}
