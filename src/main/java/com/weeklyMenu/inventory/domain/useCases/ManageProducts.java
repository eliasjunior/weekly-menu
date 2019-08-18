package com.weeklyMenu.inventory.domain.useCases;

import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
import com.weeklyMenu.inventory.dto.ProductDTO;

/**
 * ManageProducts
 */
public class ManageProducts {
  final ProductDataAccess dataAccess;
  public ManageProducts(ProductDataAccess dataAccess) {
    this.dataAccess = dataAccess;
  }
  public ProductDTO create(ProductDTO product) {
    return this.dataAccess.save(product);
  }
}