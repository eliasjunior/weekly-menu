package com.weeklyMenu.webAdaptor.data;

import com.weeklyMenu.webAdaptor.mapper.InventoryMapper;
import com.weeklyMenu.webAdaptor.model.ProductDB;
import com.weeklyMenu.webAdaptor.repository.ProductRepository;
import main.java.com.weeklyMenu.entity.Product;
import main.java.com.weeklyMenu.gateway.ProductGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductGatewayImpl implements ProductGateway {
    private final ProductRepository productRepository;
    private final InventoryMapper MAPPER = InventoryMapper.INSTANCE;

    public ProductGatewayImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        List<ProductDB> products = productRepository.findAll();
        return MAPPER.listProductsDBToProducts(products);
    }

    @Override
    public Product create(Product product) {
        ProductDB dbMapper = MAPPER.productToProductDB(product);
        dbMapper.updateBasic(null);
        return MAPPER.productDBToProduct(productRepository.save(dbMapper));
    }

    @Override
    public Product findByNameIgnoreCase(String name) {
        return MAPPER.productDBToProduct(productRepository.findByNameIgnoreCase(name));
    }

    @Override
    public Product findByNameIgnoreCaseAndIdIsDiff(String name, String id) {
        return MAPPER.productDBToProduct(productRepository.findByNameIgnoreCaseAndIdIsDiff(name, id));
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
        return MAPPER
                .productDBToProduct(productRepository.findById(id).get());
    }

    @Override
    public Product findByName(String name) {
        return MAPPER.productDBToProduct(productRepository.findByName(name));
    }

    @Override
    public Optional<Product> findById(String id) {
        Optional<ProductDB> optional = productRepository.findById(id);
        return Optional.of(MAPPER.productDBToProduct(optional.get()));
    }
}
