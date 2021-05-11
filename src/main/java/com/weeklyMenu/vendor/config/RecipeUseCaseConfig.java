package com.weeklyMenu.vendor.config;

import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.CategoryRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import main.java.com.weeklyMenu.Interactor.product.ManageProduct;
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
