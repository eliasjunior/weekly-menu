package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InventoryMapper {
    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    @Mapping(source = "catId", target = "category.id")
    Product productDtoToProduct(ProductDto dto);

    @Mapping(source = "category.id", target = "catId")
    ProductDto productToProductDto(Product entity);

    List<ProductDto> listProductDtoToProduct(List<Product> products);

    CategoryDto categoryToCategoryDto(Category entity);

    Category categoryDtoToCategory(CategoryDto dto);

    List<CategoryDto> categoryToCategoryDto(List<Category> categories);
}
