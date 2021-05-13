package com.weeklyMenu.adaptor.config;

import com.weeklyMenu.useCase.Interactor.cart.FindCart;
import com.weeklyMenu.useCase.Interactor.cart.ManageCart;
import com.weeklyMenu.useCase.Interactor.validator.CartValidator;
import com.weeklyMenu.useCase.Interactor.validator.ProductValidator;
import com.weeklyMenu.useCase.Interactor.validator.RecipeValidator;
import com.weeklyMenu.useCase.gateway.CartGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartUseCaseConfig  {
    private final CartGateway cartGateway;
    private final CartValidator cartValidator;
    private final RecipeValidator recipeValidator;
    private final ProductValidator productValidator;
    private final IdGenerator idGenerator;

    public CartUseCaseConfig(CartGateway cartGateway, CartValidator cartValidator, RecipeValidator recipeValidator,
                             ProductValidator productValidator, IdGenerator idGenerator) {
        this.cartGateway = cartGateway;
        this.cartValidator = cartValidator;
        this.recipeValidator = recipeValidator;
        this.productValidator = productValidator;
        this.idGenerator = idGenerator;
    }

    @Bean
    public ManageCart createManageCart() {
        return new ManageCart(cartGateway, cartValidator, recipeValidator, productValidator, idGenerator);
    }

    @Bean
    public FindCart createFindCart() {
        return new FindCart(cartGateway);
    }
}
