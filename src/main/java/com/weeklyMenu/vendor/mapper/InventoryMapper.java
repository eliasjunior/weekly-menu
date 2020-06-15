package com.weeklyMenu.vendor.mapper;

import com.weeklyMenu.dto.CategoryDTO;
import com.weeklyMenu.dto.ProductDTO;
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
    Product productDtoToProduct(ProductDTO dto);

    @Mapping(source = "category.id", target = "catId")
    ProductDTO productToProductDto(Product entity);

    List<ProductDTO> listProductDtoToProduct(List<Product> products);

    CategoryDTO categoryToCategoryDto(Category entity);

    Category categoryDtoToCategory(CategoryDTO dto);

    List<CategoryDTO> categoryToCategoryDto(List<Category> categories);
}
