package com.weeklyMenu.domain.data;

import com.weeklyMenu.dto.ProductDto;

import java.util.List;

//TODO on hold, no sure If I will go this way
public interface ProductInputBoundary {
    void create();
    List<ProductDto> getAllProducts();
}
