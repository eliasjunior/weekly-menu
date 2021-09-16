package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.CategoryDB;
import com.weeklyMenu.adaptor.model.ProductDB;
import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.entity.Product;

import java.util.List;

public interface InventoryMapper {
    ProductDB productToProductDB(Product product);

    Product productDBToProduct(ProductDB entity);

    List<Product> listProductsDBToProducts(List<ProductDB> products);

    Category categoryDBToCategory(CategoryDB entity);

    CategoryDB categoryToCategoryDB(Category category);

    List<Category> categoriesDBToCategories(List<CategoryDB> categories);
}
