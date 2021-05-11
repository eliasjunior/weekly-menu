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

    public Product getProduct(String id) {
        return productGateway.getProduct(id);
    }

    public boolean isProductNameUsed(Product dto) {
        Product product = productGateway.findByName(dto.getName());
        return product != null;
    }
}
