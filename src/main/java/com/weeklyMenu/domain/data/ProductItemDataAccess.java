package com.weeklyMenu.domain.data;

import com.weeklyMenu.domain.mapper.ProductItemMapper;
import com.weeklyMenu.domain.useCases.ProductItemDomain;

import java.util.List;

public interface ProductItemDataAccess {
    List<ProductItemMapper> getProductItems();
}
