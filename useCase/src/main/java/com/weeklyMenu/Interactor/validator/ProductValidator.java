package main.java.com.weeklyMenu.Interactor.validator;

import main.java.com.weeklyMenu.entity.Product;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.ProductGateway;

import java.util.Objects;
import java.util.Optional;

public class ProductValidator {
    private final ProductGateway productGateway;

    public ProductValidator(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Product validateProduct(String prodId) {
        if (Objects.isNull(prodId)) {
            throw new CustomValidationException("Product id from ProdDetail cannot be null");
        }
        Optional<Product> prodIn = productGateway.findById(prodId);
        if (!prodIn.isPresent()) {
            String msgError = "Attempt to retrieve product but product does not exist id=" + prodId;
            throw new CustomValidationException(msgError);
        }
        return prodIn.get();
    }

    public void validateProduct(Product product) {
        try {
            Objects.requireNonNull(product.getCatId(),
                    "Input for product creation is not valid, there is no category id");
        } catch (RuntimeException e) {
            throw new CustomValidationException(e.getMessage());
        }
    }

    public void validateProductIn(Product cart) {
        if(Objects.isNull(cart.getId())) {
            Product prodInDB = productGateway.findByNameIgnoreCase(cart.getName());
            if( Objects.nonNull(prodInDB)) {
                throw new CustomValidationException("Attempt to save new product failed, prod with this name already exists.");
            }
        } else {
            Product prodExisting = productGateway.findByNameIgnoreCaseAndIdIsDiff(cart.getName(), cart.getId());
            if( Objects.nonNull(prodExisting)) {
                throw new CustomValidationException("Attempt to save a new product has failed because there is a prod with the same name.");
            }
        }
    }
}
