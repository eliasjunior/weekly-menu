package com.weeklyMenu.vendor.config;

import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.CategoryRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import main.java.com.weeklyMenu.Interactor.category.FindCategory;
import main.java.com.weeklyMenu.Interactor.category.ManageCategory;
import main.java.com.weeklyMenu.Interactor.validator.ProductValidator;
import main.java.com.weeklyMenu.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig extends UseCaseConfig{

    public CategoryUseCaseConfig(ProductRepository productRepository, CartRepository cartRepository,
                                RecipeRepository recipeRepository, ProductValidator productValidator,
                                IdGenerator idGenerator, CategoryRepository categoryRepository) {
        super(productRepository, cartRepository, recipeRepository, productValidator, idGenerator, categoryRepository);
    }

    @Bean
    public FindCategory createFindCategory() {
        return new FindCategory(createCategoryGateway());
    }

    @Bean
    public ManageCategory createManageCategory() {
        return new ManageCategory(createCategoryGateway(), idGenerator, categoryValidator());
    }
}
