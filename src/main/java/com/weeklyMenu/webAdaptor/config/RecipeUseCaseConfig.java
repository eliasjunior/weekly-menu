package com.weeklyMenu.webAdaptor.config;

import com.weeklyMenu.webAdaptor.repository.CartRepository;
import com.weeklyMenu.webAdaptor.repository.CategoryRepository;
import com.weeklyMenu.webAdaptor.repository.ProductRepository;
import com.weeklyMenu.webAdaptor.repository.RecipeRepository;
import main.java.com.weeklyMenu.Interactor.recipe.FindRecipe;
import main.java.com.weeklyMenu.Interactor.recipe.ManageRecipe;
import main.java.com.weeklyMenu.Interactor.validator.ProductValidator;
import main.java.com.weeklyMenu.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeUseCaseConfig extends UseCaseConfig {

    public RecipeUseCaseConfig(ProductRepository productRepository,
                               CartRepository cartRepository,
                               RecipeRepository recipeRepository,
                               ProductValidator productValidator,
                               IdGenerator idGenerator,
                               CategoryRepository categoryRepository) {
        super(productRepository, cartRepository, recipeRepository, productValidator, idGenerator, categoryRepository);
    }

    @Bean
    public FindRecipe createFindProduct() {
        return new FindRecipe(createRecipeGateway());
    }

    @Bean
    public ManageRecipe createManageProduct() {
        return new ManageRecipe(createRecipeGateway(), createRecipeValidator(), idGenerator);
    }
}
