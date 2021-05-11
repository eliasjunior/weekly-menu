package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.ProdDetailDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdDetailRepository extends JpaRepository<ProdDetailDB, String> {

    List<ProdDetailDB> findByRecipeId(String id);
}
