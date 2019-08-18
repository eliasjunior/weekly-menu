package com.weeklyMenu.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.weeklyMenu.vendor.dataAccess.ProductAccessImpl;
import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
import com.weeklyMenu.inventory.dto.*;

@RestController
@RequestMapping("/weeklymenu/v1/product")
public class ProductController {
    final ProductDataAccess service;
    
    @Autowired
    ProductController(ProductDataAccess service) {
        this.service = service;
    }
    @GetMapping
    public List<ProductDTO> getProduct() {
        final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
        LOGGER.info("--> getProduct");
        return service.getAllProducts();
    }
    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO dto) {
        return service.save(dto);
    }
}