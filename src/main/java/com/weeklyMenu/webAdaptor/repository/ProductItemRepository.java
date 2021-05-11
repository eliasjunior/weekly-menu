package com.weeklyMenu.webAdaptor.repository;

import com.weeklyMenu.webAdaptor.model.CartItemDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<CartItemDB, String> {
}
