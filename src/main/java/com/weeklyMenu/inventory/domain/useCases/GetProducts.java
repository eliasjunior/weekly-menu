package com.weeklyMenu.inventory.domain.useCases;

import java.util.List;

import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
import com.weeklyMenu.inventory.domain.data.ProductInputBoundary;
import com.weeklyMenu.inventory.dto.ProductDTO;

//TODO on hold, I'll create use case for cases more complex
public class GetProducts {
    private final ProductDataAccess dataAccess;
    final ProductInputBoundary inputData;

    public GetProducts(ProductDataAccess dataAccess, ProductInputBoundary inputData) {
        this.dataAccess = dataAccess;
        this.inputData = inputData;
    }

    public List<ProductDTO> getAllProducts() {
        return this.dataAccess.getAllProducts();
    }

    public ProductDTO getProduct(Long id) {
        // TODO maybe change to Objects.requireNonNull
        if(id == null) {
            //TODO: internationalization
            throw new RuntimeException("Id product cannot be null");
        }
        return this.dataAccess.getProduct(id);
    }
}
