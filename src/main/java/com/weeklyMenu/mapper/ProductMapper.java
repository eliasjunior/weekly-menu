package com.weeklyMenu.mapper;

import com.weeklyMenu.inventory.dto.ProductDTO;
import com.weeklyMenu.vendor.model.Product;

/**
 * ProductMapper
 */
public class ProductMapper {
    public static ProductDTO entityToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static Product dtoToEntity(ProductDTO dto) {
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}