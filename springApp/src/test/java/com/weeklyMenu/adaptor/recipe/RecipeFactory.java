//package com.weeklyMenu.useCase.recipe;
//
//import com.weeklyMenu.entity.ProdDetail;
//import com.weeklyMenu.entity.Product;
//import com.weeklyMenu.entity.Recipe;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//public class RecipeFactory {
//    public static Recipe createRecipeDto(List<ProdDetail> items) {
//        Recipe recipeDto = new Recipe();
//        recipeDto.setName("Coco");
//        recipeDto.setProdsDetail(items);
//        recipeDto.setId(UUID.randomUUID().toString());
//        return recipeDto;
//    }
//
//    public static Recipe createRecipeDtoNoId(List<ProdDetail> items) {
//        Recipe recipeDto = new Recipe();
//        recipeDto.setName("Cake");
//        recipeDto.setProdsDetail(items);
//        return recipeDto;
//    }
//
//    public static ProdDetail createSingleProdDetailDto(Product productDto) {
//        ProdDetail prodDetailDto = new ProdDetail();
//        prodDetailDto.setProdId(productDto.getId());
//        prodDetailDto.setId(UUID.randomUUID().toString());
//        prodDetailDto.setQuantity(1);
//        return prodDetailDto;
//    }
//
//    public static List<ProdDetail> createSingleListProdDetailDto(Product productDto) {
//        ProdDetail prodDetailDto = new ProdDetail();
//        prodDetailDto.setProdId(productDto.getId());
//        prodDetailDto.setId(UUID.randomUUID().toString());
//        prodDetailDto.setQuantity(1);
//        return Arrays.asList(prodDetailDto);
//    }
//
//    public static List<ProdDetail> createProdDetailsDto(List<Product> prods) {
//        return prods.stream().map(productDto -> {
//            ProdDetail prodDetailDto = new ProdDetail();
//            prodDetailDto.setId(UUID.randomUUID().toString());
//            prodDetailDto.setQuantity(1);
//            prodDetailDto.setProdId(productDto.getId());
//            return prodDetailDto;
//        }).collect(Collectors.toList());
//    }
//}
