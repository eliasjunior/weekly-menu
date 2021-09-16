package com.weeklyMenu.adaptor.mapper;

import com.weeklyMenu.adaptor.model.CategoryDB;
import com.weeklyMenu.adaptor.model.ProductDB;
import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryMapperImpl implements InventoryMapper {
    @Override
    public ProductDB productToProductDB(Product product) {
        return ProductDB.builder()
                .id(product.getId())
                .name(product.getName())
                .quantityType(product.getQuantityType())
                .category(CategoryDB.builder().id(product.getCatId()).build()).build();
    }

    @Override
    public Product productDBToProduct(ProductDB entity) {
        return Product.builder().id(entity.getId())
                .id(entity.getId())
                .name(entity.getName())
                .quantityType(entity.getQuantityType())
                .catId(entity.getCategory().getId())
                .build();
    }

    @Override
    public List<Product> listProductsDBToProducts(List<ProductDB> products) {
        if(products == null) {
            return null;
        }
        return products.stream().map(p -> getProductDB(p)).collect(Collectors.toList());
    }

    @Override
    public Category categoryDBToCategory(CategoryDB entity) {
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .products(listProductsDBToProducts(entity.getProducts()))
                .build();
    }

    @Override
    public CategoryDB categoryToCategoryDB(Category category) {
        return CategoryDB.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public List<Category> categoriesDBToCategories(List<CategoryDB> categories) {
        if(categories == null) {
            return null;
        }
        return categories.stream().map(c -> getCategoryDB(c)).collect(Collectors.toList());
    }

    private Category getCategoryDB(CategoryDB cat) {
        try {
            return Category.builder()
                    .name(cat.getName())
                    .id(cat.getId())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error to convert Category DB to Category");
        }
    }

    private Product getProductDB(ProductDB p) {
        try {
            return Product.builder()
                    .name(p.getName())
                    .id(p.getId())
                    .quantityType(p.getQuantityType())
                    .catId(p.getCategory().getId())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error to convert Product DB to Product");
        }
    }
}
