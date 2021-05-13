package com.weeklyMenu.adaptor.SpringData;

import com.weeklyMenu.adaptor.model.CartDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<CartDB, String> {
    @Query("Select c from CartDB c where UPPER(c.basicEntity.status)  like 'A'")
    List<CartDB> findAllActives();
}