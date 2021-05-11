package com.weeklyMenu.webAdaptor.repository;

import com.weeklyMenu.webAdaptor.model.ProdDetailDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdDetailRepository extends JpaRepository<ProdDetailDB, String> {

    List<ProdDetailDB> findByRecipeId(String id);
}
