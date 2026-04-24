package com.weeklyMenu.adaptor.controller;

import com.weeklyMenu.useCase.Interactor.cart.FindCart;
import com.weeklyMenu.useCase.Interactor.cart.ManageCart;
import com.weeklyMenu.useCase.Interactor.category.FindCategory;
import com.weeklyMenu.useCase.Interactor.category.ManageCategory;
import com.weeklyMenu.useCase.Interactor.product.FindProduct;
import com.weeklyMenu.useCase.Interactor.product.ManageProduct;
import com.weeklyMenu.useCase.Interactor.recipe.FindRecipe;
import com.weeklyMenu.useCase.Interactor.recipe.ManageRecipe;
import com.weeklyMenu.useCase.Interactor.validator.CategoryValidator;
import com.weeklyMenu.useCase.Interactor.validator.ProductValidator;
import com.weeklyMenu.useCase.entity.Cart;
import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.entity.Product;
import com.weeklyMenu.useCase.entity.Recipe;
import com.weeklyMenu.useCase.gateway.CartGateway;
import com.weeklyMenu.useCase.gateway.CategoryGateway;
import com.weeklyMenu.useCase.gateway.ProductGateway;
import com.weeklyMenu.useCase.gateway.RecipeGateway;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class DeleteEndpointMappingTest {
    private static final String ID = "entity-id";

    @Test
    void productDeleteUsesIdPathVariable() throws Exception {
        FakeProductGateway gateway = new FakeProductGateway();
        ManageProduct manageProduct = new ManageProduct(gateway, new ProductValidator(gateway), null);
        MockMvc mvc = standaloneSetup(new ProductController(new FindProduct(gateway), manageProduct)).build();

        mvc.perform(delete("/v1/products/{id}", ID))
                .andExpect(status().isNoContent());

        assertEquals(ID, gateway.removedId);
    }

    @Test
    void categoryDeleteUsesIdPathVariable() throws Exception {
        FakeCategoryGateway gateway = new FakeCategoryGateway();
        ManageCategory manageCategory = new ManageCategory(gateway, null, new CategoryValidator(gateway));
        MockMvc mvc = standaloneSetup(new CategoryController(new FindCategory(gateway), manageCategory)).build();

        mvc.perform(delete("/v1/categories/{id}", ID))
                .andExpect(status().isNoContent());

        assertEquals(ID, gateway.removedId);
    }

    @Test
    void recipeDeleteUsesIdPathVariable() throws Exception {
        FakeRecipeGateway gateway = new FakeRecipeGateway();
        ManageRecipe manageRecipe = new ManageRecipe(gateway, null, null);
        MockMvc mvc = standaloneSetup(new RecipeController(new FindRecipe(gateway), manageRecipe)).build();

        mvc.perform(delete("/v1/recipes/{id}", ID))
                .andExpect(status().isNoContent());

        assertEquals(ID, gateway.removedId);
    }

    @Test
    void cartDeleteUsesIdPathVariable() throws Exception {
        FakeCartGateway gateway = new FakeCartGateway();
        ManageCart manageCart = new ManageCart(gateway, null, null, null, null);
        MockMvc mvc = standaloneSetup(new CartController(new FindCart(gateway), manageCart)).build();

        mvc.perform(delete("/v1/carts/{id}", ID))
                .andExpect(status().isNoContent());

        assertEquals(ID, gateway.removedId);
    }

    private static class FakeProductGateway implements ProductGateway {
        private String removedId;

        @Override public Optional<Product> findById(String id) { return Optional.empty(); }
        @Override public Product create(Product product) { return product; }
        @Override public Product findByNameIgnoreCase(String name) { return null; }
        @Override public Product findByNameIgnoreCaseAndIdIsDiff(String name, String id) { return null; }
        @Override public void edit(Product product) {}
        @Override public void remove(String id) { removedId = id; }
        @Override public Product getProduct(String id) { return null; }
        @Override public Product findByName(String name) { return null; }
        @Override public List<Product> getAllProducts() { return List.of(); }
    }

    private static class FakeCategoryGateway implements CategoryGateway {
        private String removedId;

        @Override public Category create(Category category) { return category; }
        @Override public void edit(Category category) {}
        @Override public void remove(String id) { removedId = id; }
        @Override public List<Category> getAllCategories() { return List.of(); }
        @Override public Category getCategory(String id) { return null; }
        @Override public Category findByNameIgnoreCase(String name) { return null; }
        @Override public Category findByNameIgnoreCaseAndIdIsDiff(String name, String id) { return null; }
        @Override public Optional<Category> findById(String id) { return Optional.empty(); }
    }

    private static class FakeRecipeGateway implements RecipeGateway {
        private String removedId;

        @Override public Optional<Recipe> findById(String id) { return Optional.empty(); }
        @Override public List<Recipe> getAllRecipes() { return List.of(); }
        @Override public Recipe create(Recipe recipe) { return recipe; }
        @Override public void edit(Recipe dto) {}
        @Override public void remove(String id) { removedId = id; }
        @Override public Recipe findByNameIgnoreCase(String name) { return null; }
        @Override public Recipe findByNameIgnoreCaseAndIdIsDiff(String name, String id) { return null; }
        @Override public Recipe findByName(String name) { return null; }
    }

    private static class FakeCartGateway implements CartGateway {
        private String removedId;

        @Override public Cart create(Cart cart) { return cart; }
        @Override public void edit(Cart cart) {}
        @Override public void remove(String id) { removedId = id; }
        @Override public List<Cart> getCartList() { return List.of(); }
        @Override public Optional<Cart> findById(String id) { return Optional.empty(); }
    }
}
