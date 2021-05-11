package com.weeklyMenu.webAdaptor.controller;

import main.java.com.weeklyMenu.Interactor.product.FindProduct;
import main.java.com.weeklyMenu.Interactor.product.ManageProduct;
import main.java.com.weeklyMenu.common.GlobalConstant;
import main.java.com.weeklyMenu.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(GlobalConstant.BASE_URL + "/products")
public class ProductController {
    final FindProduct findProduct;
    final ManageProduct manageProduct;
    final Logger LOGGER;

    @Autowired
    ProductController(FindProduct findProduct, ManageProduct manageProduct) {
        this.findProduct = findProduct;
        this.manageProduct = manageProduct;
        LOGGER = LoggerFactory.getLogger(ProductController.class);
    }

    @GetMapping
    public List<Product> getProducts() {
        LOGGER.info("--> getProducts");
        return findProduct.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        LOGGER.info("--> getProduct {}", id);
        return findProduct.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product productDto) {
        LOGGER.info("--> save");
        Product newDto = manageProduct.create(productDto);
        return ResponseEntity
                .created(URI.create(String.format(GlobalConstant.BASE_URL + "/products/%s", newDto.getId())))
                .body(newDto);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Product dto) {
        LOGGER.info("--> update");
        manageProduct.edit(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        manageProduct.remove(id);
        return ResponseEntity.noContent().build();
    }
}