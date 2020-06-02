package com.weeklyMenu.mapper;

import com.weeklyMenu.inventory.dto.ProductDTO;
import com.weeklyMenu.vendor.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO entityToDTO(Product entity);

    Product dtoToEntity(ProductDTO dto);

    List<ProductDTO> entityToDTO(List<Product> products);
}
