package com.weeklyMenu.vendor.repository;
import java.util.List;

import com.weeklyMenu.vendor.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product, String>{
  List<Product> findAll();
}