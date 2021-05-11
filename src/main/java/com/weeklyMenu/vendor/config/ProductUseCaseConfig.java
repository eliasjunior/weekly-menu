package com.weeklyMenu.vendor.config;

import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
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
                                IdGenerator idGenerator) {
        super(productRepository, cartRepository, recipeRepository, productValidator, idGenerator);
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
