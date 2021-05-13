package com.weeklyMenu.useCase.Interactor.product;

import com.weeklyMenu.useCase.entity.Product;
import com.weeklyMenu.useCase.gateway.ProductGateway;

import java.util.List;

public class FindProduct {
    private final ProductGateway productGateway;

    public FindProduct(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public List<Product> getAllProducts() {
        return productGateway.getAllProducts();
    }

    public Product getProduct(String id) {
        return productGateway.getProduct(id);
    }

    public boolean isProductNameUsed(Product dto) {
        Product product = productGateway.findByName(dto.getName());
        return product != null;
    }
}
