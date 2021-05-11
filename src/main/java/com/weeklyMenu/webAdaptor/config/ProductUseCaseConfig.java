package com.weeklyMenu.webAdaptor.config;

import com.weeklyMenu.webAdaptor.repository.CartRepository;
import com.weeklyMenu.webAdaptor.repository.CategoryRepository;
import com.weeklyMenu.webAdaptor.repository.ProductRepository;
import com.weeklyMenu.webAdaptor.repository.RecipeRepository;
import main.java.com.weeklyMenu.Interactor.product.FindProduct;
import main.java.com.weeklyMenu.Interactor.product.ManageProduct;
import main.java.com.weeklyMenu.Interactor.validator.ProductValidator;
import main.java.com.weeklyMenu.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCaseConfig extends UseCaseConfig{
    public ProductUseCaseConfig(ProductRepository productRepository, CartRepository cartRepository,
                                RecipeRepository recipeRepository, ProductValidator productValidator,
                                IdGenerator idGenerator, CategoryRepository categoryRepository) {
        super(productRepository, cartRepository, recipeRepository, productValidator, idGenerator, categoryRepository);
    }

    @Bean
    public FindProduct createFindProduct() {
        return new FindProduct(createProductGateway());
    }

    @Bean
    public ManageProduct createManageProduct() {
        return new ManageProduct(createProductGateway(), createProductValidator(), idGenerator);
    }
}
