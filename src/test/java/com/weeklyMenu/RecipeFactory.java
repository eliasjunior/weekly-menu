package com.weeklyMenu;

import com.weeklyMenu.domain.data.RecipeDataAccess;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.model.ProdDetail;
import com.weeklyMenu.vendor.model.Product;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecipeFactory {
    public static RecipeDto createRecipe(List<ProdDetailDto> items) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setProdsDetail(items);
        recipeDto.setId(UUID.randomUUID().toString());
        return recipeDto;
    }

    public static RecipeDto createRecipeNoId(List<ProdDetailDto> items) {
        RecipeDto recipeDto = new RecipeDto();
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
    public static ProdDetail createSingleProdDetail(Product product) {
        ProdDetail prodDetail = new ProdDetail();
        prodDetail.setProduct(product);
        prodDetail.setQuantity(1);
        return prodDetail;
    }
    public static List<ProdDetailDto> createSingleList(ProductDto productDto) {
        ProdDetailDto prodDetailDto = new ProdDetailDto();
        prodDetailDto.setProdId(productDto.getId());
        prodDetailDto.setId(UUID.randomUUID().toString());
        prodDetailDto.setQuantity(1);
        return Arrays.asList(prodDetailDto);
    }

    public static List<ProdDetailDto> createList(List<ProductDto> prods) {
        return prods.stream().map(productDto -> {
            ProdDetailDto prodDetailDto = new ProdDetailDto();
            prodDetailDto.setId(UUID.randomUUID().toString());
            prodDetailDto.setQuantity(prodDetailDto.getQuantity());
            prodDetailDto.setProdId(productDto.getId());
            return prodDetailDto;
        }).collect(Collectors.toList());
    }
}
