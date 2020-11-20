package com.weeklyMenu.vendor.controller;

import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.helpers.GlobalConstant;
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

import java.net.URI;
import java.util.List;

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
    public List<ProductDto> getProducts() {
        LOGGER.info("--> getProducts");
        return productDataAccess.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable String id) {
        LOGGER.info("--> getProduct {}", id);
        return productDataAccess.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto) {
        LOGGER.info("--> save");
        ProductDto newDto = productDataAccess.save(productDto);
        return ResponseEntity
                .created(URI.create(String.format(GlobalConstant.BASE_URL + "/products/%s", newDto.getId())))
                .body(newDto);

    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody ProductDto dto) {
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