package com.weeklyMenu.adaptor.config;

import com.weeklyMenu.useCase.Interactor.category.FindCategory;
import com.weeklyMenu.useCase.Interactor.category.ManageCategory;
import com.weeklyMenu.useCase.Interactor.validator.CategoryValidator;
import com.weeklyMenu.useCase.gateway.CategoryGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {
    private final CategoryGateway categoryGateway;
    private final IdGenerator idGenerator;
    private final CategoryValidator categoryValidator;

    public CategoryUseCaseConfig(CategoryGateway categoryGateway, IdGenerator idGenerator, CategoryValidator categoryValidator) {
        this.categoryGateway = categoryGateway;
        this.idGenerator = idGenerator;
        this.categoryValidator = categoryValidator;
    }

    @Bean
    public FindCategory createFindCategory() {
        return new FindCategory(categoryGateway);
    }

    @Bean
    public ManageCategory createManageCategory() {
        return new ManageCategory(categoryGateway, idGenerator, categoryValidator);
    }
}
