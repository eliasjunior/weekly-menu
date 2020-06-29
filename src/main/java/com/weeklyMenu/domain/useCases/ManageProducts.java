package com.weeklyMenu.domain.useCases;

import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.dto.ProductDto;

/**
 * ManageProducts, use case product crud, domain layer
 */
public class ManageProducts {
    final ProductDataAccess dataAccess;

    public ManageProducts(ProductDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public ProductDto create(ProductDto product) {
        return this.dataAccess.save(product);
    }

    public void update(ProductDto dto) {
        //TODO: validation here
        this.dataAccess.update(dto);
    }
}