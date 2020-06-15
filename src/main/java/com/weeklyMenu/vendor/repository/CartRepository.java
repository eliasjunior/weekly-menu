package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
}
