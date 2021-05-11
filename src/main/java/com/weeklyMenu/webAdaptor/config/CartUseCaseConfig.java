package com.weeklyMenu.webAdaptor.config;

import com.weeklyMenu.webAdaptor.repository.CartRepository;
import com.weeklyMenu.webAdaptor.repository.CategoryRepository;
import com.weeklyMenu.webAdaptor.repository.ProductRepository;
import com.weeklyMenu.webAdaptor.repository.RecipeRepository;
import main.java.com.weeklyMenu.Interactor.cart.FindCart;
import main.java.com.weeklyMenu.Interactor.cart.ManageCart;
import main.java.com.weeklyMenu.Interactor.validator.ProductValidator;
import main.java.com.weeklyMenu.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartUseCaseConfig extends UseCaseConfig {
    public CartUseCaseConfig(ProductRepository productRepository,
                             CartRepository cartRepository, RecipeRepository recipeRepository,
                             ProductValidator productValidator, IdGenerator idGenerator, CategoryRepository categoryRepository) {
        super(productRepository, cartRepository, recipeRepository, productValidator, idGenerator, categoryRepository);
    }

    @Bean
    public ManageCart createManageCart() {
        return new ManageCart(createCartGateway(), createCartValidator(), createRecipeValidator(), productValidator, idGenerator);
    }

    @Bean
    public FindCart createFindCart() {
        return new FindCart(createCartGateway());
    }
}