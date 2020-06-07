package com.weeklyMenu.mapper;

import com.weeklyMenu.inventory.dto.ProdDetailDto;
import com.weeklyMenu.inventory.dto.RecipeDto;
import com.weeklyMenu.vendor.model.ProdDetail;
import com.weeklyMenu.vendor.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    RecipeDto recipeToRecipeDto(Recipe recipe);
    Recipe recipeDtoToRecipe(RecipeDto recipeDto);
    List<RecipeDto> recipesToRecipesDto(List<Recipe> recipes);
    ProdDetail prodDetailDtoToProdDetail(ProdDetailDto prodDetailDto);
}
