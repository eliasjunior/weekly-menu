package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.exceptions.CustomValidationException;

import java.util.Objects;

public class InventoryValidator {
    protected void validateProductInput(ProductDto productDto) {
        try {
            Objects.requireNonNull(productDto.getCatId(),
                    "Input for product creation is not valid, there is no category id");
        }catch (RuntimeException e) {
            throw new CustomValidationException(e.getMessage());
        }
    }
}
