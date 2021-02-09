package com.weeklyMenu;

import com.weeklyMenu.domain.data.CategoryDataAccess;
import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.mapper.InventoryMapper;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BaseIntegration {
    //TODO dont use ***DataAccess here, because it can make harder to find errors.
    private CategoryDataAccess categoryDataAccess;
    private RecipeRepository recipeRepository;
    private ProductRepository productRepository;
    RecipeMapper RECIPE_MAPPER = RecipeMapper.INSTANCE;
    public BaseIntegration(CategoryDataAccess categoryDataAccess,
                           RecipeRepository recipeRepository,
                           ProductRepository productRepository) {
        this.categoryDataAccess = categoryDataAccess;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
    }

    public CategoryDto createNewCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("catTest");
        categoryDto.setId("01");
        //TODO replace by repository
        return this.categoryDataAccess.save(categoryDto);
    }

    public ProductDto productFactory(String catId, String name, String id) {
        InventoryMapper MAPPER = InventoryMapper.INSTANCE;
        Product product = new Product(id, name, "u", new Category(catId, null, null, null), null, null);
        return MAPPER.productToProductDto(productRepository.save(product));
    }

    public ProductDto createNewProductGivenCategory(String catId) {
        InventoryMapper MAPPER = InventoryMapper.INSTANCE;
        Product product = new Product(UUID.randomUUID().toString(), "NoName", "u", new Category(catId, null, null, null), null, null);
        return MAPPER.productToProductDto(productRepository.save(product));
    }

    public ProductDto createNewProduct() {
        return productFactory(createNewCategory().getId(), "apple", UUID.randomUUID().toString());
    }

    public ProductDto createNewProduct(String name, String id) {
        CategoryDto categoryDto = createNewCategory();
        return productFactory(categoryDto.getId(), name, id);
    }

    public List<CartItemDto> cartItemsFactory(String prodId, Set<String> recipes) {
        CartItemDto item = new CartItemDto();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return Arrays.asList(item);
    }

    public CartItemDto cartItemDtoFactory(String prodId, Set<String> recipes) {
        CartItemDto item = new CartItemDto();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return item;
    }

    public RecipeDto createRecipeDto(List<ProdDetailDto> recipeItems, String recipeName) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        recipe.setId(UUID.randomUUID().toString());
        recipe.setProdsDetail(RECIPE_MAPPER.recipeItemsDtosToRecipeItems(recipeItems));
        Recipe newRecipe = recipeRepository.save(recipe);
        RecipeDto  recipeDto = RECIPE_MAPPER.recipeToRecipeDto(newRecipe);
        recipeDto.setProdsDetail(RECIPE_MAPPER.recipeItemsToRecipeItemsDtos(newRecipe.getProdsDetail()));
        return recipeDto;
    }
}
