package com.weeklyMenu.vendor.repository;

import com.weeklyMenu.vendor.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, String> {

    @Query("Select c from Cart c where UPPER(c.basicEntity.status)  like 'A'")
    List<Cart> findAllActives();
}
