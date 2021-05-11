package main.java.com.weeklyMenu.Interactor.validator;

import main.java.com.weeklyMenu.entity.Category;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.CategoryGateway;

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
