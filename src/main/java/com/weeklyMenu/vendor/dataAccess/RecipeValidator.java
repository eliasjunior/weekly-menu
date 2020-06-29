package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.exceptions.CustomValidationException;

import java.util.Objects;

public class RecipeValidator {
    protected void validateInput(RecipeDto recipeDto) {
        try {
            Objects.requireNonNull(recipeDto.getProdsDetail(),
                    "Input for recipe creation is not valid, there are no products");
        }catch (RuntimeException e) {
            throw new CustomValidationException(e.getMessage());
        }
    }

}
