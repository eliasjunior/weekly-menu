package com.weeklyMenu.domain.data;


import com.weeklyMenu.dto.CategoryDTO;

import java.util.List;

/**
 * CategoryDataAccess is the abstract data access, from the domain layer
 */
public interface CategoryDataAccess {
    List<CategoryDTO> getAllCategories();

    CategoryDTO save(CategoryDTO product);

	void update(CategoryDTO dto);

    void delete(String id);
    
    CategoryDTO getCategory(String id);

    boolean isCategoryNameUsed(CategoryDTO dto);
}