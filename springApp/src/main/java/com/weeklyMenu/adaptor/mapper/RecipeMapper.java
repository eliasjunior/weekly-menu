package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.ProdDetailDB;
import com.weeklyMenu.adaptor.model.RecipeDB;
import com.weeklyMenu.useCase.entity.ProdDetail;
import com.weeklyMenu.useCase.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    Recipe recipeDBToRecipe(RecipeDB dbMapper);
    RecipeDB recipeToRecipeDB(Recipe recipe);
    List<Recipe> recipesDBToRecipes(List<RecipeDB> recipes);
    @Mapping(source = "prodId", target = "product.id")
    ProdDetailDB prodDetailToProdDetailDB(ProdDetail prodDetailDto);
    @Mapping(source = "product.id", target = "prodId")
    ProdDetail prodDetailDBToProdDetail(ProdDetailDB dbMapper);
    List<ProdDetailDB> recipeItemsToRecipeItemsDB(List<ProdDetail> recipeItems);
    List<ProdDetail> recipeItemsDBToRecipeItems(List<ProdDetailDB> dbMapperItems);
}
