package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.vendor.model.BasicEntity;
import com.weeklyMenu.vendor.model.CartItemDB;
import com.weeklyMenu.vendor.model.ProductDB;
import com.weeklyMenu.vendor.model.RecipeDB;
import main.java.com.weeklyMenu.entity.CartItem;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CartItemMapper {
    public List<CartItem> cartItemsToCartItemDtos(List<CartItemDB> cartItemDBS) {
        return cartItemDBS.stream()
                .map(cartItem -> CartItem.builder()
                        .id(cartItem.getId())
                        .name(cartItem.getName())
                        .prodId(cartItem.getProduct().getId())
                        .recipes(grabRecipeIds(cartItem.getSelectedRecipes()))
                        .selected(cartItem.isSelected()).build())
                .collect(Collectors.toList());
    }

    private Set<String> grabRecipeIds(Set<RecipeDB> selectedRecipes) {
        if (Objects.isNull(selectedRecipes)) {
            return null;
        }
        return selectedRecipes.stream()
                .map(RecipeDB::getId)
                .collect(Collectors.toSet());
    }

    public List<CartItemDB> cartItemsToCartItems(List<CartItem> items) {
        return items.stream().map(itemDto -> CartItemDB.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .product(ProductDB.builder().id(itemDto.getProdId()).build())
                .selected(itemDto.isSelected())
                .selectedRecipes(buildRecipe(itemDto.getRecipes()))
                .basicEntity(new BasicEntity())
                .build()).collect(Collectors.toList());
    }

    private Set<RecipeDB> buildRecipe(Set<String> recipeIds) {
        if (Objects.isNull(recipeIds)) {
            return null;
        }
        return recipeIds.stream()
                .map(id -> RecipeDB.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}
