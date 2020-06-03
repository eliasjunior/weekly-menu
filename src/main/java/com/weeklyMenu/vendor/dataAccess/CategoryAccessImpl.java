package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.inventory.domain.data.CategoryDataAccess;
import com.weeklyMenu.inventory.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryAccessImpl implements CategoryDataAccess {
    @Override
    public List<CategoryDTO> getAllCategories() {
        return null;
    }

    @Override
    public CategoryDTO save(CategoryDTO product) {
        return null;
    }

    @Override
    public void update(CategoryDTO dto) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public CategoryDTO getCategory(String id) {
        return null;
    }

    @Override
    public boolean isCategoryNameUsed(CategoryDTO dto) {
        return false;
    }
}
