package main.java.com.weeklyMenu.Interactor.product;

import main.java.com.weeklyMenu.entity.Product;
import main.java.com.weeklyMenu.gateway.ProductGateway;

import java.util.List;

public class FindProduct {
    private final ProductGateway productGateway;

    public FindProduct(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public List<Product> getAllProducts() {
        return productGateway.getAllProducts();
    }
}
