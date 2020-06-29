package com.weeklyMenu.domain.data;

import java.util.List;
import com.weeklyMenu.dto.ProductDto;

/**
 * ProductDataAccess is the abstract data access, from the domain layer 
 */
public interface ProductDataAccess {
    List<ProductDto> getAllProducts();

    ProductDto save(ProductDto product);

	void update(ProductDto dto);

    void delete(String id);
    
    ProductDto getProduct(String id);

    boolean isProductNameUsed(ProductDto dto);
}