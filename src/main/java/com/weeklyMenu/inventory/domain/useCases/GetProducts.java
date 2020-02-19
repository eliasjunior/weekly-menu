package com.weeklyMenu.inventory.domain.useCases;

import java.util.List;

import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
import com.weeklyMenu.inventory.dto.ProductDTO;

public class GetProducts {
    final ProductDataAccess dataAccess;

    public GetProducts(ProductDataAccess dataAccess) {
        this.dataAccess = dataAccess;
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
