package com.weeklyMenu.adaptor.springData;

import com.weeklyMenu.adaptor.model.CategoryDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<CategoryDB, String> {
    CategoryDB findByName(String name);

    @Query("SELECT c from CategoryDB c where UPPER(c.name) LIKE UPPER(?1)")
    CategoryDB findByNameIgnoreCase(String name);

    @Query("SELECT c from CategoryDB c where UPPER(c.name) LIKE UPPER(?1) AND c.id <> ?2")
    CategoryDB findByNameIgnoreCaseAndIdIsDiff(String name, String id);
}

