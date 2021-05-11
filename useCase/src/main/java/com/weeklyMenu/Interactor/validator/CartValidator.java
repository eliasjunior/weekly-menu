package main.java.com.weeklyMenu.Interactor.validator;

import main.java.com.weeklyMenu.entity.Cart;
import main.java.com.weeklyMenu.entity.CartItem;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CartValidator {
    public void validateCart(Cart cart) {
        try {
            Objects.requireNonNull(cart.getCartItems(), "List<ProductItems> from Cart cannot be null");
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

    public void validateCartAndProducts(Cart oldCart, Cart cart) {
        // validate if cartItem has already the product
        for (CartItem item : cart.getCartItems()) {
            if(!containsItem(oldCart.getCartItems(), item.getId()) && containsProdInCart(oldCart.getCartItems(), item.getProdId())) {
                throw new CustomValidationException("Attempt to update the cart has failed because the cart already has a product! and cart item is new");
            }
        }
    }
    private boolean containsItem(final List<CartItem> list, final String id){
        return list
                .stream()
                .anyMatch(cartItem -> cartItem.getId().equals(id));
    }

    private boolean containsProdInCart(final List<CartItem> list, final String prodId){
        return list
                .stream()
                .anyMatch(cartItem -> cartItem.getProduct().getId().equals(prodId));
    }
}
