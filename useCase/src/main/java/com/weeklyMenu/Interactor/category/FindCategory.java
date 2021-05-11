package main.java.com.weeklyMenu.Interactor.category;

import main.java.com.weeklyMenu.entity.Category;
import main.java.com.weeklyMenu.gateway.CategoryGateway;

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
