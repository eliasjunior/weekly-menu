package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.ProdDetailDB;
import com.weeklyMenu.adaptor.model.ProductDB;
import com.weeklyMenu.adaptor.model.RecipeDB;
import com.weeklyMenu.useCase.entity.ProdDetail;
import com.weeklyMenu.useCase.entity.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecipeMapperImpl implements RecipeMapper {
    @Override
    public Recipe recipeDBToRecipe(RecipeDB recipeDB) {
        return Recipe
                .builder().id(recipeDB.getId())
                .name(recipeDB.getName())
                .prodsDetail(getProductDetailList(recipeDB.getProdsDetail()))
                .build();
    }

    @Override
    public RecipeDB recipeToRecipeDB(Recipe recipe) {
        return RecipeDB
                .builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .prodsDetail(this.recipeItemsToRecipeItemsDB(recipe.getProdsDetail()))
                .build();
    }

    @Override
    public List<Recipe> recipesDBToRecipes(List<RecipeDB> recipeListDB) {
        return recipeListDB
                .stream()
                .map(r -> Recipe
                        .builder()
                        .id(r.getId())
                        .name(r.getName())
                        .prodsDetail(getProductDetailList(r.getProdsDetail()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProdDetailDB prodDetailToProdDetailDB(ProdDetail prodDetail) {
        return getProductDetailDB(prodDetail);
    }

    @Override
    public ProdDetail prodDetailDBToProdDetail(ProdDetailDB prodDetailDB) {
        return getProductDetail(prodDetailDB);
    }

    @Override
    public List<ProdDetailDB> recipeItemsToRecipeItemsDB(List<ProdDetail> recipeItems) {
        if(recipeItems == null) {
            return new ArrayList<>();
        }
        return recipeItems
                .stream()
                .map(r -> ProdDetailDB
                    .builder()
                    .id(r.getId())
                    .quantity(r.getQuantity())
                    .recipe(createRecipeDB(r.getId()))
                    .product(createProductDB(r.getProdId()))
                .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdDetail> recipeItemsDBToRecipeItems(List<ProdDetailDB> prodDetailDBList) {
        if(prodDetailDBList == null) {
            return new ArrayList<>();
        }
        return prodDetailDBList
                .stream()
                .map(r -> ProdDetail
                        .builder()
                        .id(r.getId())
                        .quantity(r.getQuantity())
                        .prodId(r.getProduct().getId())
                        .build())
                .collect(Collectors.toList());
    }

    private ProdDetail getProductDetail(ProdDetailDB p) {
        Objects.requireNonNull(p.getProduct(), "Product from prodDetail cannot be null");
        return ProdDetail
                .builder()
                .id(p.getId())
                .prodId(p.getProduct().getId())
                .quantity(p.getQuantity())
                .build();
    }

    private ProdDetailDB getProductDetailDB(ProdDetail p) {
        Objects.requireNonNull(p.getProdId(), "Product from prodDetail cannot be null");
        return ProdDetailDB
                .builder()
                .id(p.getId())
                .product(ProductDB.builder().id(p.getProdId()).build())
                .quantity(p.getQuantity())
                .build();
    }

    private List<ProdDetail> getProductDetailList(List<ProdDetailDB> prodsDetailList) {
        if (prodsDetailList == null) {
            return null;
        }
        return prodsDetailList
                .stream()
                .map(p -> getProductDetail(p))
                .collect(Collectors.toList());
    }

    private RecipeDB createRecipeDB(String id) {
        return RecipeDB
                .builder()
                .id(id)
                .build();
    }

    private ProductDB createProductDB(String prodId) {
        return ProductDB
                .builder()
                .id(prodId)
                .build();
    }

}
