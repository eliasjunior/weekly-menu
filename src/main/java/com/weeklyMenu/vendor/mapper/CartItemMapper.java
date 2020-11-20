package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.model.Recipe;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CartItemMapper {
    public List<CartItemDto> cartItemsToCartItemDtos(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> CartItemDto.builder()
                        .id(cartItem.getId())
                        .name(cartItem.getName())
                        .prodId(cartItem.getProduct().getId())
                        .recipes(grabRecipeIds(cartItem.getSelectedRecipes()))
                        .selected(cartItem.isSelected()).build())
                .collect(Collectors.toList());
    }

    private Set<String> grabRecipeIds(Set<Recipe> selectedRecipes) {
        if (Objects.isNull(selectedRecipes)) {
            return null;
        }
        return selectedRecipes.stream()
                .map(Recipe::getId)
                .collect(Collectors.toSet());
    }

    public List<CartItem> cartItemDtosToCartItems(List<CartItemDto> itemDtos) {
        return itemDtos.stream().map(itemDto -> CartItem.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .product(Product.builder().id(itemDto.getProdId()).build())
                .selected(itemDto.isSelected())
                .selectedRecipes(buildRecipe(itemDto.getRecipes()))
                .build()).collect(Collectors.toList());
    }

    private Set<Recipe> buildRecipe(Set<String> recipeIds) {
        if (Objects.isNull(recipeIds)) {
            return null;
        }
        return recipeIds.stream()
                .map(id -> Recipe.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}
