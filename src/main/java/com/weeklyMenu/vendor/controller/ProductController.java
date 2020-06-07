package com.weeklyMenu.vendor.controller;

import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.helpers.GlobalConstant;
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

import java.util.List;

//TODO should be into the vendor dir because the spring annotation
@RestController
@RequestMapping(GlobalConstant.BASE_URL + "/products")
public class ProductController {
    final ProductDataAccess productDataAccess;
    final Logger LOGGER;

    @Autowired
    ProductController(ProductDataAccess productDataAccess) {
        LOGGER = LoggerFactory.getLogger(ProductController.class);
        this.productDataAccess = productDataAccess;
    }

    @GetMapping
    public List<ProductDTO> getProducts() {
        LOGGER.info("--> getProducts");
        return productDataAccess.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable String id) {
        LOGGER.info("--> getProduct {}", id);
        return productDataAccess.getProduct(id);
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO productDto) {
        LOGGER.info("--> save");
        boolean isCreated = productDataAccess.isProductNameUsed(productDto);
        if (!isCreated) {
            return productDataAccess.save(productDto);
        } else {
            throw new CustomValidationException("Name already exits ", new RuntimeException());
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody ProductDTO dto) {
        LOGGER.info("--> update");
        productDataAccess.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        productDataAccess.delete(id);
        return ResponseEntity.noContent().build();
    }
}