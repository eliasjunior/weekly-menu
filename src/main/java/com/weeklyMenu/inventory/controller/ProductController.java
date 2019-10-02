package com.weeklyMenu.inventory.controller;

import java.util.List;

import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
import com.weeklyMenu.inventory.dto.ProductDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weeklymenu/v1/product")
public class ProductController {
    final ProductDataAccess service;
    final Logger LOGGER;
    
    @Autowired
    ProductController(ProductDataAccess service) {
        LOGGER = LoggerFactory.getLogger(ProductController.class);
        this.service = service;
    }
    @GetMapping
    public List<ProductDTO> getProduct() {
        LOGGER.debug("--> getProduct");
        return service.getAllProducts();
    }
    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO dto) {
        LOGGER.debug("--> save");
        return service.save(dto);
    }
    @PutMapping
    public ResponseEntity<String> update(@RequestBody ProductDTO dto) {
        LOGGER.debug("--> update"); 
        service.update(dto);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}