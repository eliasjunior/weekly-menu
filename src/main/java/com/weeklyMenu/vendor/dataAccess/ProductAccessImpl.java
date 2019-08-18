package com.weeklyMenu.vendor.dataAccess;

import java.util.List;

import com.weeklyMenu.mapper.ProductMapper;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
import com.weeklyMenu.inventory.dto.ProductDTO;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * ProductAccessImpl
 */
@Service
public class ProductAccessImpl implements ProductDataAccess {
    private final ProductRepository productRepository;

    ProductAccessImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
            .stream()
            .map(ProductMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ProductDTO save(ProductDTO dto) {
        Product entity = productRepository.save(ProductMapper.dtoToEntity(dto));
        return ProductMapper.entityToDTO(entity);
    }
}
