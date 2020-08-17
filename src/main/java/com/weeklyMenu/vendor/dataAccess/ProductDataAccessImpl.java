package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.vendor.mapper.InventoryMapper;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDataAccessImpl implements ProductDataAccess {
    private final ProductRepository productRepository;
    private final InventoryMapper MAPPER = InventoryMapper.INSTANCE;
    private IdGenerator idGenerator;
    private DataAccessValidator validator;

    ProductDataAccessImpl(ProductRepository productRepository,
                          IdGenerator idGenerator,
                          DataAccessValidator validator) {
        this.productRepository = productRepository;
        this.idGenerator = idGenerator;
        this.validator = validator;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return MAPPER.listProductDtoToProduct(products);
    }

    @Override
    public ProductDto save(ProductDto dto) {
        this.validator.validateProductDto(dto);
        if (dto.getId() == null) {
            dto.setId(idGenerator.generateId());
        }
        Product product = productRepository.save(MAPPER.productDtoToProduct(dto));
        return MAPPER.productToProductDto(product);
    }

    @Override
    public void update(ProductDto dto) {
        Optional<Product> optional = productRepository.findById(dto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Product not found to update", new RuntimeException());
        }
        productRepository.save(MAPPER.productDtoToProduct(dto));
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProduct(String id) {
        return MAPPER
                .productToProductDto(productRepository.findById(id).get());
    }

    @Override
    public boolean isProductNameUsed(ProductDto dto) {
        Product product = productRepository.findByName(dto.getName());
        return product != null;
    }
}
