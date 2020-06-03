package com.weeklyMenu.vendor.dataAccess;

import java.util.List;

import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.mapper.ProductMapper;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
import com.weeklyMenu.inventory.dto.ProductDTO;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ProductAccessImpl
 */
@Service
public class ProductAccessImpl implements ProductDataAccess {
    private final ProductRepository productRepository;
    private ProductMapper MAPPER = ProductMapper.INSTANCE;

    ProductAccessImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return MAPPER.entityToDTO(products);
    }

    @Override
    public ProductDTO save(ProductDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        Product entity = productRepository.save(MAPPER.dtoToEntity(dto));
        return MAPPER.entityToDTO(entity);
    }

    @Override
    public void update(ProductDTO dto) {
        Optional<Product> optional = productRepository.findById(dto.getId());
        if(!optional.isPresent()) {
            throw new CustomValidationException("Product not found to update", new RuntimeException());
        }
        productRepository.save(MAPPER.dtoToEntity(dto));
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProduct(String id) {
        return MAPPER
            .entityToDTO(productRepository.findById(id).get());
    }

    @Override
    public boolean isProductNameUsed(ProductDTO dto) {
        Product product = productRepository.findByName(dto.getName());
        return product != null;
    }
}
