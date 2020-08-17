package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<CartItem, String> {
}
