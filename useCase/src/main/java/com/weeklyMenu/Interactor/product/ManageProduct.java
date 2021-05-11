package main.java.com.weeklyMenu.Interactor.product;

import main.java.com.weeklyMenu.Interactor.validator.ProductValidator;
import main.java.com.weeklyMenu.entity.Product;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.ProductGateway;
import main.java.com.weeklyMenu.generator.IdGenerator;

import java.util.Objects;
import java.util.Optional;

public class ManageProduct {
    private final ProductGateway productGateway;
    private final ProductValidator productValidator;
    private final IdGenerator idGenerator;

    public ManageProduct(ProductGateway productGateway, ProductValidator productValidator, IdGenerator idGenerator) {
        this.productGateway = productGateway;
        this.productValidator = productValidator;
        this.idGenerator = idGenerator;
    }

    public Product create(Product product) {
        productValidator.validateProduct(product);
        if (product.getId() == null) {
            product.setId(idGenerator.generateId());
        }
        productValidator.validateProductIn(product);

        return productGateway.create(product);
    }

    public void edit(Product product) {
        productValidator.validateProduct(product);
        productValidator.validateProductIn(product);
        productGateway.edit(product);
    }

    public boolean isProductNameUsed(Product dto) {
        Product product = productGateway.findByName(dto.getName());
        return product != null;
    }
}
