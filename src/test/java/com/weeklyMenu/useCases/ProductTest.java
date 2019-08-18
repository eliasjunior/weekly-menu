// package com.weeklyMenu.useCases;

// import static org.junit.Assert.assertEquals;

// import com.weeklyMenu.inventory.domain.data.ProductDataAccess;
// import com.weeklyMenu.inventory.domain.useCases.GetProducts;
// import com.weeklyMenu.inventory.domain.useCases.ManageProducts;
// import com.weeklyMenu.inventory.dto.ProductDTO;

// import org.junit.Test;
// import org.mockito.Mockito;

// /**
//  * ProductTest
//  */
// public class ProductTest {
  
//   @Test
//   public void shouldSaveProduct() {
//     ProductDataAccess dataAccess = Mockito.mock(ProductDataAccess.class);
//     ManageProducts store = new ManageProducts(dataAccess);
//     GetProducts getProducts = new GetProducts(dataAccess);

//     ProductDTO dto = new ProductDTO();
//     dto.setName("name");
//     dto.setId("1234");

//     store.create(dto);
//     assertEquals(0, getProducts.getAllProducts().size());
//   }

// }