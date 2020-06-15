package com.weeklyMenu.domain.data;

import com.weeklyMenu.dto.RecipeDto;

import java.util.List;

public interface RecipeDataAccess {
    List<RecipeDto> getAllRecipes();

    RecipeDto save(RecipeDto recipe);

    void update(RecipeDto dto);

    void delete(String id);

    RecipeDto getRecipe(String id);

    boolean isRecipeNameUsed(RecipeDto dto);
}
