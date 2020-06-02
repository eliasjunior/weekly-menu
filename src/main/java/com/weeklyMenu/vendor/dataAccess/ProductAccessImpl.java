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
    private ProductMapper MAPPER = ProductMapper.INSTANCE;

    ProductAccessImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
            .stream()
            .map(MAPPER::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ProductDTO save(ProductDTO dto) {
        Product entity = productRepository.save(MAPPER.dtoToEntity(dto));
        return MAPPER.entityToDTO(entity);
    }

    @Override
    public void update(ProductDTO dto) {
        productRepository.save(MAPPER.dtoToEntity(dto));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id.toString());
    }

    @Override
    public ProductDTO getProduct(Long id) {
        //TODO: validation on domain layer;
        ProductDTO dto = MAPPER
            .entityToDTO(productRepository.findById(id.toString()).get());
        return dto;
    }

    @Override
    public boolean isProductNameUsed(ProductDTO dto) {
        Product product = productRepository.findByName(dto.getName());
        return product != null;
    }
}
