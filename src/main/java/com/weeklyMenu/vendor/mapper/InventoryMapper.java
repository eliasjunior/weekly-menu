package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.vendor.model.CategoryDB;
import com.weeklyMenu.vendor.model.ProductDB;
import main.java.com.weeklyMenu.entity.Category;
import main.java.com.weeklyMenu.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InventoryMapper {
    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    @Mapping(source = "catId", target = "category.id")
    ProductDB productToProductDB(Product product);

    @Mapping(source = "category.id", target = "catId")
    Product productDBToProduct(ProductDB entity);

    List<Product> listProductsDBToProducts(List<ProductDB> products);

    Category categoryDBToCategory(CategoryDB entity);

    CategoryDB categoryToCategoryDB(Category category);

    List<Category> categoriesDBToCategories(List<CategoryDB> categories);
}
