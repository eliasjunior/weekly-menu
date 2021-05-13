package com.weeklyMenu.useCase.Interactor.cart;

import com.weeklyMenu.useCase.Interactor.validator.CartValidator;
import com.weeklyMenu.useCase.Interactor.validator.ProductValidator;
import com.weeklyMenu.useCase.Interactor.validator.RecipeValidator;
import com.weeklyMenu.useCase.entity.Cart;
import com.weeklyMenu.useCase.entity.CartItem;
import com.weeklyMenu.useCase.exceptions.CustomValidationException;
import com.weeklyMenu.useCase.gateway.CartGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageCart {
    private final CartGateway cartGateway;
    private final CartValidator cartValidator;
    private final RecipeValidator recipeValidator;
    private final ProductValidator productValidator;
    private final IdGenerator idGenerator;

    public ManageCart(CartGateway cartGateway, CartValidator cartValidator,
                      RecipeValidator recipeValidator, ProductValidator productValidator,
                      IdGenerator idGenerator) {
        this.cartGateway = cartGateway;
        this.cartValidator = cartValidator;
        this.recipeValidator = recipeValidator;
        this.productValidator = productValidator;
        this.idGenerator = idGenerator;
    }

    public Cart create(Cart cart) {
        this.cartValidator.validateCart(cart);
        if (cart.getId() == null) {
            cart.setId(idGenerator.generateId());
            cart.setCartItems(generateIdProdItem(cart.getCartItems()));
        }
        cart.getCartItems().forEach(cartItem -> {
            recipeValidator.validateRecipes(cartItem.getRecipes());
            productValidator.validateProduct(cartItem.getProdId());
        });
        return this.cartGateway.create(cart);
    }

    public void edit(Cart cart) {
        cartValidator.validateCart(cart);

        cart.setCartItems(generateIdProdItem(cart.getCartItems()));
        cartValidator.validateCartAndProducts(findById(cart.getId()), cart);
        cart.getCartItems().forEach(cartItem -> {
            recipeValidator.validateRecipes(cartItem.getRecipes());
            productValidator.validateProduct(cartItem.getProdId());
        });
        this.cartGateway.edit(cart);
    }

    public void remove(String id) {
        cartGateway.remove(id);
    }

    public Cart findById(String id) {
        Optional<Cart> optional = cartGateway.findById(id);
        if (optional.isEmpty()) {
            throw new CustomValidationException("Attempt to retrieve cart has failed!");
        }
        return optional.get();
    }

    private List<CartItem> generateIdProdItem(List<CartItem> Items) {
        if (Objects.isNull(Items)) {
            throw new CustomValidationException("There is not product item to save the cart");
        }
        return Items
                .stream()
                .peek(cartItem -> {
                    if (Objects.isNull(cartItem.getId())) {
                        cartItem.setId(idGenerator.generateId());
                    }
                })
                .collect(Collectors.toList());
    }
}
