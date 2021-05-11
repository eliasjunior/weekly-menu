package main.java.com.weeklyMenu.Interactor.category;

import main.java.com.weeklyMenu.Interactor.validator.CategoryValidator;
import main.java.com.weeklyMenu.entity.Category;
import main.java.com.weeklyMenu.entity.Product;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.CategoryGateway;
import main.java.com.weeklyMenu.generator.IdGenerator;

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