package com.weeklyMenu.useCase.Interactor.validator;

import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.exceptions.CustomValidationException;
import com.weeklyMenu.useCase.gateway.CategoryGateway;

import java.util.Objects;

public class CategoryValidator {
    private final CategoryGateway categoryGateway;

    public CategoryValidator(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    public void validateName(Category category) {
        if(Objects.isNull(category.getId())) {
            Category catInDb = categoryGateway.findByNameIgnoreCase(category.getName());
            if( Objects.nonNull(catInDb)) {
                throw new CustomValidationException("Attempt to save new category failed, cat with this name already exists.");
            }
        } else {
            Category catExisting = categoryGateway.findByNameIgnoreCaseAndIdIsDiff(category.getName(), category.getId());
            if( Objects.nonNull(catExisting)) {
                throw new CustomValidationException("Attempt to save a new category has failed because there is a cat with the same name.");
            }
        }
    }
}
