package com.weeklyMenu;

import com.weeklyMenu.domain.data.CategoryDataAccess;
import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.repository.RecipeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BaseIntegration {
    //TODO dont use ***DataAccess here, because it can make harder to find errors.
    private CategoryDataAccess categoryDataAccess;
    private ProductDataAccess productDataAccess;
    private RecipeRepository recipeRepository;
    RecipeMapper RECIPE_MAPPER = RecipeMapper.INSTANCE;
    public BaseIntegration(CategoryDataAccess categoryDataAccess,
                           ProductDataAccess productDataAccess,
                           RecipeRepository recipeRepository) {
        this.categoryDataAccess = categoryDataAccess;
        this.productDataAccess = productDataAccess;
        this.recipeRepository = recipeRepository;
    }

    public CategoryDto categoryFactory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("catTest");
        categoryDto.setId("01");
        return this.categoryDataAccess.save(categoryDto);
    }

    public ProductDto productFactory(String catId) {
        return productFactory(catId, "apple");
    }

    public ProductDto productFactory(String catId, String name) {
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setCatId(catId);
        return productDataAccess.save(productDto);
    }

    public List<CartItemDto> cartItemsFactory(String prodId, Set<String> recipes) {
        CartItemDto item = new CartItemDto();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return Arrays.asList(item);
    }

    public CartItemDto cartItemFactory(String prodId, Set<String> recipes) {
        CartItemDto item = new CartItemDto();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return item;
    }

    public ProductDto createNewProduct() {
        return createNewProduct("apple");
    }

    public ProductDto createNewProduct(String name) {
        CategoryDto categoryDto = categoryFactory();
        return productFactory(categoryDto.getId(), name);
    }

    public RecipeDto createRecipe(List<ProdDetailDto> recipeItems) {
        Recipe recipe = new Recipe();
        recipe.setId(UUID.randomUUID().toString());
        recipe.setProdsDetail(RECIPE_MAPPER.recipeItemsDtosToRecipeItems(recipeItems));
        Recipe newRecipe = recipeRepository.save(recipe);
        RecipeDto  recipeDto = RECIPE_MAPPER.recipeToRecipeDto(newRecipe);
        recipeDto.setProdsDetail(RECIPE_MAPPER.recipeItemsToRecipeItemsDtos(newRecipe.getProdsDetail()));
        return recipeDto;
    }
}
