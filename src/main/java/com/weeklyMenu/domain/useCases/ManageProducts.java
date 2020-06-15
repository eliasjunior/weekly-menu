package com.weeklyMenu.domain.useCases;

import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.dto.ProductDTO;

/**
 * ManageProducts, use case product crud, domain layer
 */
public class ManageProducts {
    final ProductDataAccess dataAccess;

    public ManageProducts(ProductDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public ProductDTO create(ProductDTO product) {
        return this.dataAccess.save(product);
    }

    public void update(ProductDTO dto) {
        //TODO: validation here
        this.dataAccess.update(dto);
    }
}