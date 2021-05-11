package com.weeklyMenu.vendor.config;

import com.weeklyMenu.vendor.dataAccess.CartAccessDataImpl;
import com.weeklyMenu.vendor.dataAccess.ProductDataAccessImpl;
import com.weeklyMenu.vendor.dataAccess.RecipeAccessDataImpl;
import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import main.java.com.weeklyMenu.Interactor.validator.CartValidator;
import main.java.com.weeklyMenu.Interactor.validator.ProductValidator;
import main.java.com.weeklyMenu.Interactor.validator.RecipeValidator;
import main.java.com.weeklyMenu.gateway.CartGateway;
import main.java.com.weeklyMenu.gateway.ProductGateway;
import main.java.com.weeklyMenu.gateway.RecipeGateway;
import main.java.com.weeklyMenu.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    protected final ProductRepository productRepository;
    protected final CartRepository cartRepository;
    protected final RecipeRepository recipeRepository;
    protected final ProductValidator productValidator;
    protected final IdGenerator idGenerator;

    public UseCaseConfig(ProductRepository productRepository,
                         CartRepository cartRepository, RecipeRepository recipeRepository,
                         ProductValidator productValidator, IdGenerator idGenerator) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.recipeRepository = recipeRepository;
        this.productValidator = productValidator;
        this.idGenerator = idGenerator;
    }

    @Bean
    public ProductGateway createProductGateway() {
        return new ProductDataAccessImpl(productRepository);
    }

    @Bean
    public CartGateway createCartGateway() {
        return new CartAccessDataImpl(cartRepository);
    }

    @Bean
    public RecipeGateway createRecipeImpl() {
        return new RecipeAccessDataImpl(recipeRepository);
    }

    @Bean
    public CartValidator createCartValidator() {
        return new CartValidator();
    }

    @Bean
    public ProductValidator createProductValidator() {
        return new ProductValidator(createProductGateway());
    }

    @Bean
    public RecipeValidator createRecipeValidator() {
        return new RecipeValidator(createRecipeImpl(), createProductValidator());
    }
}
