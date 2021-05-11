package main.java.com.weeklyMenu.Interactor.validator;

import main.java.com.weeklyMenu.entity.Cart;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;

import java.util.Objects;

public class CartValidator {
    public void validateCart(Cart cart) {
        try {
            Objects.requireNonNull(cart.getCartItems(), "List<ProductItems> from CartDto cannot be null");
            if(cart.getCartItems().size() == 0) {
                throw new CustomValidationException("Attempt to create a new cart but there is not item");
            }
            cart.getCartItems().forEach(item -> {
                Objects.requireNonNull(item.getProdId(),
                        "Product (item) ID from cart is missing");
                if (Objects.nonNull(item.getRecipes())) {
                    item.getRecipes().forEach(recId -> {
                        Objects.requireNonNull(recId,
                                "Recipe (recipes[{id}]) ID from cart is missing");
                    });
                }
            });

        } catch (RuntimeException e) {
            throw new CustomValidationException(e.getMessage());
        }
    }
}
