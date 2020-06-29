package com.weeklyMenu.domain.useCases;

import java.util.List;

import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.domain.data.ProductInputBoundary;
import com.weeklyMenu.dto.ProductDto;

//TODO on hold, I'll create use case for cases more complex
public class GetProducts {
    private final ProductDataAccess dataAccess;
    final ProductInputBoundary inputData;

    public GetProducts(ProductDataAccess dataAccess, ProductInputBoundary inputData) {
        this.dataAccess = dataAccess;
        this.inputData = inputData;
    }

    public List<ProductDto> getAllProducts() {
        return this.dataAccess.getAllProducts();
    }

    public ProductDto getProduct(String id) {
        // TODO maybe change to Objects.requireNonNull
        if(id == null) {
            //TODO: internationalization
            throw new RuntimeException("Id product cannot be null");
        }
        return this.dataAccess.getProduct(id);
    }
}
