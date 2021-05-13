package com.weeklyMenu.adaptor.SpringData;

import com.weeklyMenu.adaptor.model.CartItemDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<CartItemDB, String> {
}
