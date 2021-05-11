package com.weeklyMenu.webAdaptor.config;

import com.weeklyMenu.webAdaptor.dataAccess.CartAccessDataImpl;
import com.weeklyMenu.webAdaptor.dataAccess.CategoryDataAccessImpl;
import com.weeklyMenu.webAdaptor.dataAccess.ProductDataAccessImpl;
import com.weeklyMenu.webAdaptor.dataAccess.RecipeAccessDataImpl;
import com.weeklyMenu.webAdaptor.helper.BasicGenerator;
import com.weeklyMenu.webAdaptor.repository.CartRepository;
import com.weeklyMenu.webAdaptor.repository.CategoryRepository;
import com.weeklyMenu.webAdaptor.repository.ProductRepository;
import com.weeklyMenu.webAdaptor.repository.RecipeRepository;
import main.java.com.weeklyMenu.Interactor.validator.CartValidator;
import main.java.com.weeklyMenu.Interactor.validator.CategoryValidator;
import main.java.com.weeklyMenu.Interactor.validator.ProductValidator;
import main.java.com.weeklyMenu.Interactor.validator.RecipeValidator;
import main.java.com.weeklyMenu.gateway.CartGateway;
import main.java.com.weeklyMenu.gateway.CategoryGateway;
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
    private final CategoryRepository categoryRepository;

    public UseCaseConfig(ProductRepository productRepository,
                         CartRepository cartRepository, RecipeRepository recipeRepository,
                         ProductValidator productValidator, IdGenerator idGenerator, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.recipeRepository = recipeRepository;
        this.productValidator = productValidator;
        this.idGenerator = idGenerator;
        this.categoryRepository = categoryRepository;
    }
    @Bean
    public IdGenerator getIdGenerator() {
        return new BasicGenerator();
    }

    @Bean
    public CategoryValidator categoryValidator() {
        return new CategoryValidator(createCategoryGateway());
    }

    public CategoryGateway createCategoryGateway() {
        return new CategoryDataAccessImpl(categoryRepository);
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
    public RecipeGateway createRecipeGateway() {
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
        return new RecipeValidator(createRecipeGateway(), createProductValidator());
    }
}