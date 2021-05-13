package com.weeklyMenu.useCase.Interactor.product;

import com.weeklyMenu.useCase.Interactor.validator.ProductValidator;
import com.weeklyMenu.useCase.entity.Product;
import com.weeklyMenu.useCase.gateway.ProductGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;

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

    public void remove(String id) {
        productGateway.remove(id);
    }

}
