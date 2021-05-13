package com.weeklyMenu.adaptor.config;

import com.weeklyMenu.adaptor.springData.CartRepository;
import com.weeklyMenu.adaptor.springData.CategoryRepository;
import com.weeklyMenu.adaptor.springData.ProductRepository;
import com.weeklyMenu.adaptor.springData.RecipeRepository;
import com.weeklyMenu.adaptor.gateway.CartGatewayImpl;
import com.weeklyMenu.adaptor.gateway.CategoryGatewayImpl;
import com.weeklyMenu.adaptor.gateway.ProductGatewayImpl;
import com.weeklyMenu.adaptor.gateway.RecipeGatewayImpl;
import com.weeklyMenu.adaptor.helper.BasicGenerator;
import com.weeklyMenu.adaptor.mapper.CartItemMapper;
import com.weeklyMenu.adaptor.mapper.CartMapper;
import com.weeklyMenu.adaptor.mapper.InventoryMapper;
import com.weeklyMenu.adaptor.mapper.RecipeMapper;
import com.weeklyMenu.useCase.Interactor.validator.CartValidator;
import com.weeklyMenu.useCase.Interactor.validator.CategoryValidator;
import com.weeklyMenu.useCase.Interactor.validator.ProductValidator;
import com.weeklyMenu.useCase.Interactor.validator.RecipeValidator;
import com.weeklyMenu.useCase.gateway.CartGateway;
import com.weeklyMenu.useCase.gateway.CategoryGateway;
import com.weeklyMenu.useCase.gateway.ProductGateway;
import com.weeklyMenu.useCase.gateway.RecipeGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final Logger logger;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final RecipeMapper recipeMapper;
    private final InventoryMapper inventoryMapper;

    public UseCaseConfig(ProductRepository productRepository,
                         CartRepository cartRepository, RecipeRepository recipeRepository,
                         CategoryRepository categoryRepository,
                         Logger logger1, CartMapper cartMapper,
                         CartItemMapper cartItemMapper, RecipeMapper recipeMapper,
                         InventoryMapper inventoryMapper) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.logger = logger1;
        this.cartMapper = cartMapper;
        this.cartItemMapper = cartItemMapper;
        this.recipeMapper = recipeMapper;
        this.inventoryMapper = inventoryMapper;
    }

    @Bean
    public IdGenerator getIdGenerator() {
        return new BasicGenerator();
    }

    @Bean
    public CategoryValidator categoryValidator() {
        return new CategoryValidator(createCategoryGateway());
    }

    @Bean
    public CategoryGateway createCategoryGateway() {
        return new CategoryGatewayImpl(categoryRepository, inventoryMapper);
    }

    @Bean
    public ProductGateway createProductGateway() {
        return new ProductGatewayImpl(productRepository, inventoryMapper);
    }

    @Bean
    public CartGateway createCartGateway() {
        return new CartGatewayImpl(logger, cartRepository, cartMapper, cartItemMapper);
    }

    @Bean
    public RecipeGateway createRecipeGateway() {
        return new RecipeGatewayImpl(logger, recipeRepository, recipeMapper);
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
        return new RecipeValidator(createRecipeGateway(), createProductValidator(), logger);
    }
}
