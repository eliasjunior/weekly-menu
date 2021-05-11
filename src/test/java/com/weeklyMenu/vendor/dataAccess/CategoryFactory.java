package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.vendor.model.CategoryDB;

public class CategoryFactory {
    public static CategoryDB createCategory(String name) {
        CategoryDB category = new CategoryDB();
        category.setName("catTest");
        category.setId("01");
        return category;
    }
}
