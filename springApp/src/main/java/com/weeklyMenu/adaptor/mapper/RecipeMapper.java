package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.ProdDetailDB;
import com.weeklyMenu.adaptor.model.RecipeDB;
import com.weeklyMenu.useCase.entity.ProdDetail;
import com.weeklyMenu.useCase.entity.Recipe;

import java.util.List;

public interface RecipeMapper {
    Recipe recipeDBToRecipe(RecipeDB dbMapper);
    RecipeDB recipeToRecipeDB(Recipe recipe);
    List<Recipe> recipesDBToRecipes(List<RecipeDB> recipes);
    ProdDetailDB prodDetailToProdDetailDB(ProdDetail prodDetailDto);
    ProdDetail prodDetailDBToProdDetail(ProdDetailDB dbMapper);
    List<ProdDetailDB> recipeItemsToRecipeItemsDB(List<ProdDetail> recipeItems);
    List<ProdDetail> recipeItemsDBToRecipeItems(List<ProdDetailDB> dbMapperItems);
}
