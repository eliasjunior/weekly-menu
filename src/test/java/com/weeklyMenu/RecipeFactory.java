package com.weeklyMenu;

import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecipeFactory {
    public static RecipeDto createRecipeDto(List<ProdDetailDto> items) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Coco");
        recipeDto.setProdsDetail(items);
        recipeDto.setId(UUID.randomUUID().toString());
        return recipeDto;
    }

    public static RecipeDto createRecipeDtoNoId(List<ProdDetailDto> items) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Cake");
        recipeDto.setProdsDetail(items);
        return recipeDto;
    }

    public static ProdDetailDto createSingleProdDetailDto(ProductDto productDto) {
        ProdDetailDto prodDetailDto = new ProdDetailDto();
        prodDetailDto.setProdId(productDto.getId());
        prodDetailDto.setId(UUID.randomUUID().toString());
        prodDetailDto.setQuantity(1);
        return prodDetailDto;
    }

    public static List<ProdDetailDto> createSingleListProdDetailDto(ProductDto productDto) {
        ProdDetailDto prodDetailDto = new ProdDetailDto();
        prodDetailDto.setProdId(productDto.getId());
        prodDetailDto.setId(UUID.randomUUID().toString());
        prodDetailDto.setQuantity(1);
        return Arrays.asList(prodDetailDto);
    }

    public static List<ProdDetailDto> createProdDetailsDto(List<ProductDto> prods) {
        return prods.stream().map(productDto -> {
            ProdDetailDto prodDetailDto = new ProdDetailDto();
            prodDetailDto.setId(UUID.randomUUID().toString());
            prodDetailDto.setQuantity(1);
            prodDetailDto.setProdId(productDto.getId());
            return prodDetailDto;
        }).collect(Collectors.toList());
    }
}
