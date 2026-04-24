package com.weeklyMenu.adaptor.gateway;

import com.weeklyMenu.adaptor.mapper.RecipeMapper;
import com.weeklyMenu.adaptor.model.RecipeDB;
import com.weeklyMenu.adaptor.springData.RecipeRepository;
import com.weeklyMenu.useCase.entity.Recipe;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

class RecipeGatewayImplTest {
    @Test
    void findByIdReturnsMappedRecipeWhenRepositoryFindsOne() {
        RecipeDB recipeDB = RecipeDB.builder().id("recipe-id").name("Soup").build();
        Recipe recipe = Recipe.builder().id("recipe-id").name("Soup").build();
        RecipeRepository repository = repositoryReturning(recipeDB);
        RecipeMapper mapper = mapperReturning(recipe);
        RecipeGatewayImpl gateway = new RecipeGatewayImpl(getLogger(RecipeGatewayImplTest.class), repository, mapper);

        Optional<Recipe> result = gateway.findById("recipe-id");

        assertTrue(result.isPresent());
        assertEquals(recipe, result.get());
    }

    private RecipeRepository repositoryReturning(RecipeDB recipeDB) {
        return (RecipeRepository) Proxy.newProxyInstance(
                RecipeRepository.class.getClassLoader(),
                new Class<?>[]{RecipeRepository.class},
                (proxy, method, args) -> {
                    if ("findById".equals(method.getName())) {
                        return Optional.of(recipeDB);
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
    }

    private RecipeMapper mapperReturning(Recipe recipe) {
        return new RecipeMapper() {
            @Override public Recipe recipeDBToRecipe(RecipeDB recipeDB) { return recipe; }
            @Override public RecipeDB recipeToRecipeDB(Recipe recipe) { throw new UnsupportedOperationException(); }
            @Override public List<Recipe> recipesDBToRecipes(List<RecipeDB> recipeListDB) { throw new UnsupportedOperationException(); }
            @Override public com.weeklyMenu.adaptor.model.ProdDetailDB prodDetailToProdDetailDB(com.weeklyMenu.useCase.entity.ProdDetail prodDetail) { throw new UnsupportedOperationException(); }
            @Override public com.weeklyMenu.useCase.entity.ProdDetail prodDetailDBToProdDetail(com.weeklyMenu.adaptor.model.ProdDetailDB prodDetailDB) { throw new UnsupportedOperationException(); }
            @Override public List<com.weeklyMenu.adaptor.model.ProdDetailDB> recipeItemsToRecipeItemsDB(List<com.weeklyMenu.useCase.entity.ProdDetail> recipeItems) { throw new UnsupportedOperationException(); }
            @Override public List<com.weeklyMenu.useCase.entity.ProdDetail> recipeItemsDBToRecipeItems(List<com.weeklyMenu.adaptor.model.ProdDetailDB> prodDetailDBList) { throw new UnsupportedOperationException(); }
        };
    }
}
