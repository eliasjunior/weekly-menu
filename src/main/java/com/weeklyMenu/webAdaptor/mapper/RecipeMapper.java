package com.weeklyMenu.webAdaptor.mapper;

import com.weeklyMenu.webAdaptor.model.ProdDetailDB;
import com.weeklyMenu.webAdaptor.model.RecipeDB;
import main.java.com.weeklyMenu.entity.ProdDetail;
import main.java.com.weeklyMenu.entity.Recipe;
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
