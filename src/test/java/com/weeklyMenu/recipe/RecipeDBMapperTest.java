package com.weeklyMenu.recipe;

import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.webAdaptor.mapper.RecipeMapper;
import com.weeklyMenu.webAdaptor.model.ProdDetailDB;
import com.weeklyMenu.webAdaptor.model.ProductDB;
import com.weeklyMenu.webAdaptor.model.RecipeDB;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeDBMapperTest {
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

        RecipeDB recipe = new RecipeDB();
        recipe.setId(UUID.randomUUID().toString());
        recipe.setName("Greek Salad");

        ProdDetailDB prodDetailDB = new ProdDetailDB();
        prodDetailDB.setId("1");
        prodDetailDB.setQuantity(2);
       // prodDetail.setProdId("prod1");

        ProductDB product = new ProductDB();
        product.setId("prod1");
        prodDetailDB.setProduct(product);

        List<ProdDetailDB> prodsDetail = new ArrayList<>();
        prodsDetail.add(prodDetailDB);

        recipe.setProdsDetail(prodsDetail);

        System.out.println("#################");
        System.out.println(recipeMapper.recipeToRecipeDto(recipe));
    }

}
