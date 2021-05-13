//package com.weeklyMenu.useCase.data;
//
//import com.weeklyMenu.data.mapper.InventoryMapper;
//import com.weeklyMenu.data.model.CategoryDB;
//import com.weeklyMenu.data.model.ProductDB;
//import com.weeklyMenu.entity.Category;
//import com.weeklyMenu.entity.Product;
//import com.weeklyMenu.gateway.CategoryGateway;
//import com.weeklyMenu.gateway.RecipeGateway;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.UUID;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class InventoryTest {
//    @Autowired
//    CategoryGateway categoryDataAccess;
//    @Autowired
//    RecipeGateway recipeAccessData;
//
//    @Test
//    public void testMapperDtoToEntity() {
//        InventoryMapper inventoryMapper = InventoryMapper.INSTANCE;
//        Category categoryDTO = Category.builder().build();
//        categoryDTO.setId(UUID.randomUUID().toString());
//
//        System.out.println(inventoryMapper.categoryToCategoryDB(categoryDTO));
//
//        Product productDTO = new Product();
//        productDTO.setName("Sugar");
//        productDTO.setCatId(categoryDTO.getId());
//        System.out.println("&&&&& --> #################");
//        System.out.println(inventoryMapper.productToProductDB(productDTO));
//    }
//
//    @Test
//    public void testMapperEntityToDto() {
//        InventoryMapper inventoryMapper = InventoryMapper.INSTANCE;
//        ProductDB product = new ProductDB();
//        product.setName("Sugar");
//        product.setId("4321");
//        product.setQuantityType("33");
//
//        CategoryDB category = new CategoryDB();
//        category.setId("1234");
//        product.setCategory(category);
//
//        System.out.println("&&&&& --> #################");
//        System.out.println(inventoryMapper.productDBToProduct(product));
//    }
//}
