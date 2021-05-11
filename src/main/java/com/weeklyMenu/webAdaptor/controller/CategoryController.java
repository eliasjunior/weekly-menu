package com.weeklyMenu.webAdaptor.controller;

import com.weeklyMenu.webAdaptor.controller.model.CategoryWeb;
import main.java.com.weeklyMenu.Interactor.category.FindCategory;
import main.java.com.weeklyMenu.Interactor.category.ManageCategory;
import main.java.com.weeklyMenu.common.GlobalConstant;
import main.java.com.weeklyMenu.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(GlobalConstant.BASE_URL + "/categories")
public class CategoryController {
    final FindCategory findCategory;
    final ManageCategory manageCategory;
    final Logger LOGGER;

    @Autowired
    CategoryController(FindCategory findCategory, ManageCategory manageCategory) {
        this.manageCategory = manageCategory;
        LOGGER = LoggerFactory.getLogger(CategoryController.class);
        this.findCategory = findCategory;
    }

    @GetMapping
    public List<CategoryWeb> getCategories() {
        LOGGER.info("--> getCategories");
        return findCategory
                .getAllCategories().stream()
                   .map(CategoryWeb::toCategoryWeb)
                   .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public CategoryWeb getCategory(@PathVariable String id) {
        LOGGER.info("--> getCategory {}", id);
        return CategoryWeb.toCategoryWeb(findCategory.getCategory(id));
    }
    @PostMapping
    public CategoryWeb create(@RequestBody CategoryWeb categoryWeb) {
        LOGGER.info("--> save");
        Category category = manageCategory.create(categoryWeb.toCategory());
        return CategoryWeb.toCategoryWeb(category);
    }
    @PutMapping
    public ResponseEntity<String> update(@RequestBody CategoryWeb categoryWeb) {
        LOGGER.info("--> update");
        manageCategory.edit(categoryWeb.toCategory());
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        manageCategory.remove(id);
        return ResponseEntity.noContent().build();
    }
}
