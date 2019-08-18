package com.weeklyMenu.inventory.domain.data;

import java.util.List;
import com.weeklyMenu.inventory.dto.ProductDTO;

/**
 * ProductDataAccess
 */
public interface ProductDataAccess {
    List<ProductDTO> getAllProducts();

    ProductDTO save(ProductDTO product);
}