package com.weeklyMenu.inventory.domain.useCases;

import java.util.List;
import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
import com.weeklyMenu.inventory.dto.ProductDTO;

public class GetProducts {
  final ProductDataAccess dataAccess;

  public GetProducts(ProductDataAccess dataAccess) {
    this.dataAccess = dataAccess;
  }

  public List<ProductDTO> getAllProducts() {
    return this.dataAccess.getAllProducts();
  }

}