package com.weeklyMenu.recipe;

import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.ProdDetail;
import com.weeklyMenu.vendor.model.Recipe;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeMapperTest {
    @Test
    public void testMapperDtoToEntity() {
        RecipeMapper recipeMapper = RecipeMapper.INSTANCE;

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(UUID.randomUUID().toString());
        recipeDto.setName("Greek Salad");

        ProdDetailDto prodDetailDto = new ProdDetailDto();
        prodDetailDto.setId("1");
        prodDetailDto.setQuantity(2);
        prodDetailDto.setProdId("prod1");

        List<ProdDetailDto> prodsDetail = new ArrayList<>();
        prodsDetail.add(prodDetailDto);

        recipeDto.setProdsDetail(prodsDetail);

        System.out.println("#################");
        System.out.println(recipeMapper.recipeDtoToRecipe(recipeDto));
    }

    @Test
    public void testMapperEntityToDto() {
        RecipeMapper recipeMapper = RecipeMapper.INSTANCE;

        Recipe recipe = new Recipe();
        recipe.setId(UUID.randomUUID().toString());
        recipe.setName("Greek Salad");

        ProdDetail prodDetail = new ProdDetail();
        prodDetail.setId("1");
        prodDetail.setQuantity(2);
        prodDetail.setProdId("prod1");

        List<ProdDetail> prodsDetail = new ArrayList<>();
        prodsDetail.add(prodDetail);

        recipe.setProdsDetail(prodsDetail);

        System.out.println("#################");
        System.out.println(recipeMapper.recipeToRecipeDto(recipe));
    }

}
