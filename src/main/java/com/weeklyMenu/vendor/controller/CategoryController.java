package com.weeklyMenu.vendor.controller;

import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.helpers.GlobalConstant;
import com.weeklyMenu.domain.data.CategoryDataAccess;
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

@RestController
@RequestMapping(GlobalConstant.BASE_URL + "/categories")
public class CategoryController {
    final CategoryDataAccess categoryDataAccess;
    final Logger LOGGER;

    @Autowired
    CategoryController(CategoryDataAccess categoryDataAccess) {
        LOGGER = LoggerFactory.getLogger(CategoryController.class);
        this.categoryDataAccess = categoryDataAccess;
    }

    @GetMapping
    public List<CategoryDto> getCategories() {
        LOGGER.info("--> getCategories");
        return categoryDataAccess.getAllCategories();
    }
    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable String id) {
        LOGGER.info("--> getCategory {}", id);
        return categoryDataAccess.getCategory(id);
    }
    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto dto) {
        LOGGER.info("--> save");
        boolean isCreated = categoryDataAccess.isCategoryNameUsed(dto);
        if(!isCreated) {
            return categoryDataAccess.save(dto);
        } else {
            throw new CustomValidationException("Name already exits ", new RuntimeException());
        }
    }
    @PutMapping
    public ResponseEntity<String> update(@RequestBody CategoryDto dto) {
        LOGGER.info("--> update");
        categoryDataAccess.update(dto);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        categoryDataAccess.delete(id);
        return ResponseEntity.noContent().build();
    }
}
