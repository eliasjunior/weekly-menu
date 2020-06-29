package com.weeklyMenu;

import com.weeklyMenu.domain.data.CategoryDataAccess;
import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.vendor.dataAccess.ProductDataAccessImpl;

public abstract class BaseIntegration {
    public ProductDto createProduct(CategoryDataAccess categoryDataAccess,
                                    ProductDataAccessImpl productDataAccessImpl) {
        CategoryDto category = new CategoryDto();
        category.setName("Fruits");

        CategoryDto catNew = categoryDataAccess.save(category);

        ProductDto productDto = new ProductDto();
        productDto.setName("apple");
        productDto.setCatId(catNew.getId());

        ProductDto productDtoNew = productDataAccessImpl.save(productDto);

        System.out.println("################ PROD saved Success " + productDtoNew.getId());
        System.out.println("################# Cat saved Success " + catNew.getId());
        return productDto;
    }
}
