package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.ProdDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdDetailRepository extends JpaRepository<ProdDetail, String> {

    List<ProdDetail> findByRecipeId(String id);
}
