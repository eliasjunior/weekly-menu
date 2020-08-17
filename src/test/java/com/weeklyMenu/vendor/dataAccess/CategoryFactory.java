package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.vendor.model.Category;

public class CategoryFactory {
    public static Category createCategory(String name) {
        Category category = new Category();
        category.setName("catTest");
        category.setId("01");
        return category;
    }
}
