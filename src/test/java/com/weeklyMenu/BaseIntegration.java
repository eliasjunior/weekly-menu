package com.weeklyMenu;

import com.weeklyMenu.vendor.mapper.InventoryMapper;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.CategoryDB;
import com.weeklyMenu.vendor.model.ProductDB;
import com.weeklyMenu.vendor.model.RecipeDB;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import main.java.com.weeklyMenu.entity.*;
import main.java.com.weeklyMenu.gateway.CategoryGateway;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.codehaus.groovy.runtime.InvokerHelper.asList;

public class BaseIntegration {
    //TODO dont use ***DataAccess here, because it can make harder to find errors.
    private CategoryGateway categoryDataAccess;
    private RecipeRepository recipeRepository;
    private ProductRepository productRepository;
    RecipeMapper RECIPE_MAPPER = RecipeMapper.INSTANCE;
    public BaseIntegration(CategoryGateway categoryDataAccess,
                           RecipeRepository recipeRepository,
                           ProductRepository productRepository) {
        this.categoryDataAccess = categoryDataAccess;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
    }

    public Category createNewCategory() {
        Category categoryDto = Category.builder().build();
        categoryDto.setName("catTest");
        categoryDto.setId("01");
        return this.categoryDataAccess.create(categoryDto);
    }

    public Product productFactory(String catId, String name, String id) {
        InventoryMapper MAPPER = InventoryMapper.INSTANCE;
        ProductDB product = new ProductDB(id, name, "u", new CategoryDB(catId, null, null, null), null, null);
        return MAPPER.productDBToProduct(productRepository.save(product));
    }

    public Product createNewProductGivenCategory(String catId) {
        InventoryMapper MAPPER = InventoryMapper.INSTANCE;
        ProductDB product = new ProductDB(UUID.randomUUID().toString(), "NoName", "u", new CategoryDB(catId, null, null, null), null, null);
        return MAPPER.productDBToProduct(productRepository.save(product));
    }

    public Product createNewProduct() {
        return productFactory(createNewCategory().getId(), "apple", UUID.randomUUID().toString());
    }

    public Product createNewProduct(String name, String id) {
        Category categoryDto = createNewCategory();
        return productFactory(categoryDto.getId(), name, id);
    }

    public List<CartItem> cartItemsFactory(String prodId, Set<String> recipes) {
        CartItem item = new CartItem();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return asList(item);
    }

    public CartItem cartItemDtoFactory(String prodId, Set<String> recipes) {
        CartItem item = new CartItem();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return item;
    }

    public Recipe createRecipeDto(List<ProdDetail> recipeItems, String recipeName) {
        RecipeDB recipe = new RecipeDB();
        recipe.setName(recipeName);
        recipe.setId(UUID.randomUUID().toString());
        recipe.setProdsDetail(RECIPE_MAPPER.recipeItemsToRecipeItemsDB(recipeItems));
        RecipeDB newRecipe = recipeRepository.save(recipe);
        Recipe recipeDto = RECIPE_MAPPER.recipeDBToRecipe(newRecipe);
        recipeDto.setProdsDetail(RECIPE_MAPPER.recipeItemsDBToRecipeItems(newRecipe.getProdsDetail()));
        return recipeDto;
    }
}
