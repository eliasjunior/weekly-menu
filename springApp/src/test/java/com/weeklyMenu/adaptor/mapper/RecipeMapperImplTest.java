package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.ProdDetailDB;
import com.weeklyMenu.adaptor.model.ProductDB;
import com.weeklyMenu.adaptor.model.RecipeDB;
import com.weeklyMenu.useCase.entity.ProdDetail;
import com.weeklyMenu.useCase.entity.Recipe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RecipeMapperImplTest {
    RecipeMapper recipeMapper = new RecipeMapperImpl();
    @Test
    void testConvertRecipeDBToRecipe() {
        RecipeDB recipeDB = RecipeDB.builder().name("Cake").id("123").build();
        Recipe recipe = recipeMapper.recipeDBToRecipe(recipeDB);

        assertEquals(recipeDB.getName(), recipe.getName());
        assertEquals(recipeDB.getId(), recipe.getId());
    }

    @Test
    void testConvertRecipeToRecipeDB() {
        Recipe recipe = createRecipe("cake", "123");
        RecipeDB recipeDB = recipeMapper.recipeToRecipeDB(recipe);
        assertEquals(recipe.getName(), recipeDB.getName());
        assertEquals(recipe.getId(), recipeDB.getId());
        assertEquals(recipe.getProdsDetail().size(), recipeDB.getProdsDetail().size());
    }

    @Test
    void testConvertRecipesDBToRecipes() {
        RecipeDB recipeDB = recipeMapper.recipeToRecipeDB(createRecipe("orange", "34"));

        List<RecipeDB> listDB = new ArrayList<>();
        listDB.add(recipeDB);

        List<Recipe> list = recipeMapper.recipesDBToRecipes(listDB);

        assertEquals(listDB.get(0).getId(), list.get(0).getId());
        assertEquals(listDB.get(0).getName(), list.get(0).getName());
        assertEquals(listDB.get(0).getProdsDetail().get(0).getProduct().getId(),
                list.get(0).getProdsDetail().get(0).getProdId());
    }

    @Test
    void testConvertProdDetailToProdDetailDB() {
        ProdDetail prodDetail = createProdDetail("123");
        ProdDetailDB prodDetailDB = recipeMapper.prodDetailToProdDetailDB(prodDetail);

        assertEquals(prodDetail.getProdId(), prodDetailDB.getProduct().getId());
        assertEquals(prodDetail.getId(), prodDetailDB.getId());
    }

    @Test
    void testConvertProdDetailDBToProdDetail() {
        ProdDetailDB prodDetailDB = createProdDetailDB("123");
        ProdDetail prodDetail = recipeMapper.prodDetailDBToProdDetail(prodDetailDB);
        assertEquals(prodDetailDB.getProduct().getId(), prodDetail.getProdId());
        assertEquals(prodDetailDB.getId(), prodDetail.getId());
    }

    @Test
    void testConvertRecipeItemsToRecipeItemsDB() {
        List<ProdDetailDB> prodDetailDBList = recipeMapper
                .recipeItemsToRecipeItemsDB(createProdDetailList(Arrays.asList("3", "4")));

        assertEquals("3", prodDetailDBList.get(0).getId());
        assertEquals("prod_3", prodDetailDBList.get(0).getProduct().getId());
        assertEquals("4", prodDetailDBList.get(1).getId());
    }

    @Test
    void testConvertRecipeItemsDBToRecipeItems() {
        List<ProdDetail> prodDetailList = recipeMapper
                .recipeItemsDBToRecipeItems(createProdDetailDBList(Arrays.asList("99", "100")));

        assertEquals("99", prodDetailList.get(0).getId());
        assertEquals("prod_99", prodDetailList.get(0).getProdId());
        assertEquals("100", prodDetailList.get(1).getId());
    }

    private List<ProdDetail> createProdDetailList(List<String> names) {
        return names.stream()
                .map(n -> createProdDetail(n))
                .collect(Collectors.toList());
    }

    private List<ProdDetailDB> createProdDetailDBList(List<String> names) {
        return names.stream()
                .map(n -> createProdDetailDB(n))
                .collect(Collectors.toList());
    }

    private ProdDetailDB createProdDetailDB(String idx) {
        return ProdDetailDB.builder()
                .id(idx)
                .product(ProductDB.builder().id("prod_"+idx).build())
                .quantity(1)
                .build();
    }

    private ProdDetail createProdDetail(String idx) {
        return ProdDetail.builder()
                .id(idx)
                .prodId("prod_"+idx)
                .quantity(1)
                .build();
    }

    private Recipe createRecipe(String name, String id) {
        return Recipe.builder()
                .name(name)
                .id(id)
                .prodsDetail(createProdDetailList(Arrays.asList("prod1")))
                .build();
    }
}