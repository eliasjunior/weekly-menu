package com.weeklyMenu.recipe;

import com.weeklyMenu.inventory.dto.ProdDetailDto;
import com.weeklyMenu.inventory.dto.RecipeDto;
import com.weeklyMenu.mapper.RecipeMapper;
import com.weeklyMenu.vendor.dataAccess.RecipeAccessDataImpl;
import com.weeklyMenu.vendor.model.ProdDetail;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.repository.ProdDetailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeTest {

    @Autowired
    private RecipeAccessDataImpl recipeAccessData;
    @Autowired
    private ProdDetailRepository prodDetailRepository;

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

        System.out.println("Recipe #################");
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

        System.out.println("Recipe DTO #################");
        System.out.println(recipeMapper.recipeToRecipeDto(recipe));
    }

    @Test
    public void simpleCrud() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Greek Salad");

        ProdDetailDto prodDetailDto = new ProdDetailDto();
        prodDetailDto.setQuantity(2);
        prodDetailDto.setProdId("prod1");


        List<ProdDetailDto> prodsDetail = new ArrayList<>();
        prodsDetail.add(prodDetailDto);

        recipeDto.setProdsDetail(prodsDetail);

        recipeAccessData.save(recipeDto);

        System.out.println("Save ProdDetail #################");

        List<RecipeDto> recipes = recipeAccessData.getAllRecipes();
        System.out.println("CRUD RECIPE #################");
        recipes.forEach(recipeDto1 -> {
            for (ProdDetailDto prodDetail : recipeDto1.getProdsDetail()) {
                System.out.println(prodDetail.getProdId());
                System.out.println(prodDetail.getQuantity());
            }
        });

        assertEquals(1, recipes.get(0).getProdsDetail().size());
    }
}
