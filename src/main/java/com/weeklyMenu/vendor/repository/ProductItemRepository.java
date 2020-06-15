package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItem, String> {
}
