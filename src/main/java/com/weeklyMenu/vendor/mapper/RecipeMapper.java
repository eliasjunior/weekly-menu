package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.model.ProdDetail;
import com.weeklyMenu.vendor.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    RecipeDto recipeToRecipeDto(Recipe recipe);
    Recipe recipeDtoToRecipe(RecipeDto recipeDto);
    List<RecipeDto> recipesToRecipesDto(List<Recipe> recipes);
    @Mapping(source = "prodId", target = "product.id")
    ProdDetail prodDetailDtoToProdDetail(ProdDetailDto prodDetailDto);
    @Mapping(source = "product.id", target = "prodId")
    ProdDetailDto prodDetailToProdDetailDto(ProdDetail prodDetail);
    List<ProdDetail> recipeItemsDtosToRecipeItems(List<ProdDetailDto> recipeItemsDtos);
    List<ProdDetailDto> recipeItemsToRecipeItemsDtos(List<ProdDetail> recipeItems);
}
