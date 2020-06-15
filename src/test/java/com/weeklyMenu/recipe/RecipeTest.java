package com.weeklyMenu.recipe;

import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.dataAccess.RecipeAccessDataImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeTest {

    @Autowired
    private RecipeAccessDataImpl recipeAccessData;

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