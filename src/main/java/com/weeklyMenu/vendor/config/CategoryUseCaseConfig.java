package com.weeklyMenu.vendor.config;

import com.weeklyMenu.vendor.dataAccess.CategoryDataAccessImpl;
import com.weeklyMenu.vendor.repository.CategoryRepository;
import main.java.com.weeklyMenu.Interactor.category.FindCategory;
import main.java.com.weeklyMenu.Interactor.category.ManageCategory;
import main.java.com.weeklyMenu.Interactor.validator.CategoryValidator;
import main.java.com.weeklyMenu.gateway.CategoryGateway;
import main.java.com.weeklyMenu.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {
    private IdGenerator idGenerator;
    private final CategoryRepository categoryRepository;

    public CategoryUseCaseConfig(IdGenerator idGenerator, CategoryRepository categoryRepository) {
        this.idGenerator = idGenerator;
        this.categoryRepository = categoryRepository;
    }

    @Bean
    public CategoryGateway createCategoryImpl() {
        return new CategoryDataAccessImpl(categoryRepository);
    }

    @Bean
    public FindCategory createFindCategory() {
        return new FindCategory(createCategoryImpl());
    }

    @Bean
    public CategoryValidator categoryValidator() {
        return new CategoryValidator(createCategoryImpl());
    }

    @Bean
    public ManageCategory createManageCategory() {
        return new ManageCategory(createCategoryImpl(), idGenerator, categoryValidator());
    }
}
