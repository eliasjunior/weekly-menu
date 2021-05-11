package main.java.com.weeklyMenu.gateway;

import main.java.com.weeklyMenu.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {
    Category create(Category category);
    void edit(Category category);
    void remove(String id);
    List<Category> getAllCategories();
    Category getCategory(String id);
    Category findByNameIgnoreCase(String name);
    Category findByNameIgnoreCaseAndIdIsDiff(String name, String id);
    Optional<Category> findById(String id);
}
