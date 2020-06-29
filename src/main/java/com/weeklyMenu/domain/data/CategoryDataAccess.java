package com.weeklyMenu.domain.data;


import com.weeklyMenu.dto.CategoryDto;

import java.util.List;

/**
 * CategoryDataAccess is the abstract data access, from the domain layer
 */
public interface CategoryDataAccess {
    List<CategoryDto> getAllCategories();

    CategoryDto save(CategoryDto product);

	void update(CategoryDto dto);

    void delete(String id);
    
    CategoryDto getCategory(String id);

    boolean isCategoryNameUsed(CategoryDto dto);
}