package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.CartItemDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<CartItemDB, String> {
}
