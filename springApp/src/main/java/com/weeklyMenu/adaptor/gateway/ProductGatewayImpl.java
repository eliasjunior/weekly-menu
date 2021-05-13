package com.weeklyMenu.adaptor.gateway;

import com.weeklyMenu.adaptor.springData.ProductRepository;
import com.weeklyMenu.useCase.gateway.ProductGateway;
import com.weeklyMenu.adaptor.mapper.InventoryMapper;
import com.weeklyMenu.adaptor.model.ProductDB;
import com.weeklyMenu.useCase.entity.Product;

import java.util.List;
import java.util.Optional;

public class ProductGatewayImpl implements ProductGateway {
    private final ProductRepository productRepository;
    private final InventoryMapper mapper;

    public ProductGatewayImpl(ProductRepository productRepository, InventoryMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<Product> getAllProducts() {
        List<ProductDB> products = productRepository.findAll();
        return mapper.listProductsDBToProducts(products);
    }

    @Override
    public Product create(Product product) {
        ProductDB dbMapper = mapper.productToProductDB(product);
        dbMapper.updateBasic(null);
        return mapper.productDBToProduct(productRepository.save(dbMapper));
    }

    @Override
    public Product findByNameIgnoreCase(String name) {
        return mapper.productDBToProduct(productRepository.findByNameIgnoreCase(name));
    }

    @Override
    public Product findByNameIgnoreCaseAndIdIsDiff(String name, String id) {
        return mapper.productDBToProduct(productRepository.findByNameIgnoreCaseAndIdIsDiff(name, id));
    }

    @Override
    public void edit(Product product) {
        ProductDB oldProduct = productRepository.findByName(product.getName());
        oldProduct.updateBasic(oldProduct.getBasicEntity());
        productRepository.save(oldProduct);
    }

    @Override
    public void remove(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(String id) {
        return mapper
                .productDBToProduct(productRepository.findById(id).get());
    }

    @Override
    public Product findByName(String name) {
        return mapper.productDBToProduct(productRepository.findByName(name));
    }

    @Override
    public Optional<Product> findById(String id) {
        Optional<ProductDB> optional = productRepository.findById(id);
        return Optional.of(mapper.productDBToProduct(optional.get()));
    }
}
