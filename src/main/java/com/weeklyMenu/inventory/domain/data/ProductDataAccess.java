package com.weeklyMenu.inventory.domain.data;

import java.util.List;
import com.weeklyMenu.inventory.dto.ProductDTO;

/**
 * ProductDataAccess is the abstract data access, from the domain layer 
 */
public interface ProductDataAccess {
    List<ProductDTO> getAllProducts();

    ProductDTO save(ProductDTO product);

	void update(ProductDTO dto);

    void delete(String id);
    
    ProductDTO getProduct(String id);

    boolean isProductNameUsed(ProductDTO dto);
}