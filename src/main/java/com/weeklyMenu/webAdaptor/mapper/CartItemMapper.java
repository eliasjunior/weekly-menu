package com.weeklyMenu.webAdaptor.mapper;

import com.weeklyMenu.webAdaptor.model.BasicEntity;
import com.weeklyMenu.webAdaptor.model.CartItemDB;
import com.weeklyMenu.webAdaptor.model.ProductDB;
import com.weeklyMenu.webAdaptor.model.RecipeDB;
import main.java.com.weeklyMenu.entity.CartItem;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CartItemMapper {
    public List<CartItem> cartItemsDBToCartItems(List<CartItemDB> cartItemDBS) {
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
        return items.stream().map(item -> CartItemDB.builder()
                .id(item.getId())
                .name(item.getName())
                .product(ProductDB.builder().id(item.getProdId()).build())
                .selected(item.isSelected())
                .selectedRecipes(buildRecipe(item.getRecipes()))
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
