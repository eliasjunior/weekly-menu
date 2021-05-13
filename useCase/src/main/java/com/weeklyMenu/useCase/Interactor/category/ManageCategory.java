package com.weeklyMenu.useCase.Interactor.category;

import com.weeklyMenu.useCase.Interactor.validator.CategoryValidator;
import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.entity.Product;
import com.weeklyMenu.useCase.exceptions.CustomValidationException;
import com.weeklyMenu.useCase.gateway.CategoryGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ManageCategory {
    private final CategoryGateway categoryGateway;
    private final IdGenerator idGenerator;
    private final CategoryValidator categoryValidator;

    public ManageCategory(CategoryGateway categoryGateway, IdGenerator idGenerator, CategoryValidator categoryValidator) {
        this.categoryGateway = categoryGateway;
        this.idGenerator = idGenerator;
        this.categoryValidator = categoryValidator;
    }

    public Category create(Category category) {
        if (category.getId() == null) {
            category.setId(idGenerator.generateId());
        }
        if (category.getProdIds() != null && category.getProdIds().size() > 0) {
            category.setProducts(category.getProdIds()
                    .stream()
                    .map(prodId -> new Product(prodId))
                    .collect(Collectors.toList()));
        }
        categoryValidator.validateName(category);
        return categoryGateway.create(category);
    }

    public void edit(Category category) {
        Optional<Category> optional = categoryGateway.findById(category.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Category not found to update", new RuntimeException());
        }
        Category oldCat = optional.get();
        if(Objects.isNull(oldCat)) {
            throw new CustomValidationException("Update failed because the category id sent by the request was not found!");
        }
        categoryValidator.validateName(category);
        categoryGateway.edit(category);
    }

    public void remove(String id) {
        categoryGateway.remove(id);
    }
}