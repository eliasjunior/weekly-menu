package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.CartDB;
import com.weeklyMenu.adaptor.model.CartItemDB;
import com.weeklyMenu.adaptor.model.ProductDB;
import com.weeklyMenu.adaptor.model.RecipeDB;
import com.weeklyMenu.useCase.entity.Cart;
import com.weeklyMenu.useCase.entity.CartItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CartMapperImpl implements CartMapper {
    @Override
    public Cart cartDBToCart(CartDB cartDB) {
        return Cart
                .builder()
                .id(cartDB.getId())
                .name(cartDB.getName())
                .cartItems(createCartItems(cartDB.getCartItems()))
                .build();
    }

    @Override
    public List<Cart> cartsDBToCarts(List<CartDB> carts) {
        return carts
                .stream()
                .map(cartDB -> Cart
                        .builder()
                        .id(cartDB.getId())
                        .cartItems(createCartItems(cartDB.getCartItems()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CartDB cartToCartDB(Cart cart) {
        return CartDB
                .builder()
                .id(cart.getId())
                .name(cart.getName())
                .cartItems(createCartItemsDB(cart.getCartItems()))
                .build();
    }

    private List<CartItemDB> createCartItemsDB(List<CartItem> cartItems) {
        return cartItems
                .stream()
                .map(c -> CartItemDB
                        .builder()
                        .id(c.getId())
                        .selected(c.isSelected())
                        .selectedRecipes(createRecipesDB(c.getRecipes()))
                        .product(ProductDB.builder()
                                .id(c.getProdId())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    private Set<RecipeDB> createRecipesDB(Set<String> recipes) {
        return recipes
                .stream()
                .map(r -> RecipeDB
                        .builder()
                        .id(r)
                        .build())
                .collect(Collectors.toSet());
    }

    private List<CartItem> createCartItems(List<CartItemDB> cartItems) {
        if(cartItems == null) {
            return new ArrayList<>();
        }
        return cartItems
                .stream()
                .map(c -> CartItem
                        .builder()
                        .id(c.getId())
                        .name(c.getName())
                        .prodId(c.getProduct().getId())
                        .recipes(createRecipes(c.getSelectedRecipes()))
                        .selected(c.isSelected()).build())
                .collect(Collectors.toList());
    }

    private Set<String> createRecipes(Set<RecipeDB> selectedRecipes) {
        if (selectedRecipes == null) {
            return new HashSet<>();
        }
        return selectedRecipes
                .stream()
                .map(r -> r.getId())
                .collect(Collectors.toSet());
    }


}
