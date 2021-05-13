package com.weeklyMenu.useCase.Interactor.category;

import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.gateway.CategoryGateway;

import java.util.List;

public class FindCategory {
    private final CategoryGateway categoryGateway;

    public FindCategory(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    public List<Category> getAllCategories() {
        return categoryGateway.getAllCategories();
    }

    public Category getCategory(String id) {
        return categoryGateway.getCategory(id);
    }
}
