package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.CategoryDB;
import com.weeklyMenu.adaptor.model.ProductDB;
import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryMapperImplTest {
    InventoryMapper inventoryMapper = new InventoryMapperImpl();

    @Test
    void testConvertProductToProductDB() {
        Product product = Product.builder()
                .id("123")
                .name("prodA")
                .catId("431")
                .build();

        ProductDB productDB = inventoryMapper.productToProductDB(product);

        assertEquals( "123", productDB.getId());
        assertEquals( "prodA", productDB.getName());
        assertEquals( "431", productDB.getCategory().getId());
    }

    @Test
    void testConvertProductDBToProduct() {
        ProductDB productDB = ProductDB.builder()
                .id("12")
                .name("ProdA")
                .quantityType("SOME")
                .category(CategoryDB.builder().id("cat1").build())
                .build();

        Product product = inventoryMapper.productDBToProduct(productDB);

        assertEquals( productDB.getId(), product.getId());
        assertEquals( productDB.getName(), product.getName());
        assertEquals( productDB.getQuantityType(), product.getQuantityType());
    }

    @Test
    void testConvertListProductsDBToProducts() {
        ProductDB productDB = ProductDB.builder()
                .id("12")
                .name("ProdA")
                .quantityType("SOME")
                .category(CategoryDB.builder().id("cat1").build())
                .build();

        List<ProductDB> list = new ArrayList<>();
        list.add(productDB);

        List<Product> result = inventoryMapper.listProductsDBToProducts(list);

        assertEquals(productDB.getId(), result.get(0).getId());
        assertEquals(productDB.getName(), result.get(0).getName());
        assertEquals(productDB.getQuantityType(), result.get(0).getQuantityType());
    }

    @Test
    void testConvertCategoryDBToCategory() {
        CategoryDB categoryDB = CategoryDB.builder()
                .id("12")
                .build();
        Category category = inventoryMapper.categoryDBToCategory(categoryDB);
        assertEquals(categoryDB.getId(), category.getId());
    }

    @Test
    void testConvertCategoryToCategoryDB() {
        Category category = Category.builder().id("A").name("Mellon").build();

        CategoryDB categoryDB = inventoryMapper.categoryToCategoryDB(category);

        assertEquals(category.getId(), categoryDB.getId());
    }

    @Test
    void testConvertCategoriesDBToCategories() {
        List<CategoryDB> list = new ArrayList<>();

        list.add(CategoryDB.builder().id("1").name("Cat1").build());

        List<Category> convert = inventoryMapper.categoriesDBToCategories(list);

        assertEquals(list.get(0).getId(), convert.get(0).getId());
    }
}