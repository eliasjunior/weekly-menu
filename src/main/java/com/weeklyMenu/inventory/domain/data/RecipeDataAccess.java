package com.weeklyMenu.inventory.domain.data;

import com.weeklyMenu.inventory.dto.RecipeDto;

import java.util.List;

public interface RecipeDataAccess {
    List<RecipeDto> getAllRecipes();

    RecipeDto save(RecipeDto recipe);

    void update(RecipeDto dto);

    void delete(String id);

    RecipeDto getRecipe(String id);

    boolean isRecipeNameUsed(RecipeDto dto);
}
