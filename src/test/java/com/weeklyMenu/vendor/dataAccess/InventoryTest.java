package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.vendor.model.CategoryDB;
import main.java.com.weeklyMenu.useCase.data.CategoryDataAccess;
import main.java.com.weeklyMenu.useCase.data.ProductDataAccess;
import main.java.com.weeklyMenu.useCase.data.RecipeDataAccess;
import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.vendor.mapper.InventoryMapper;
import com.weeklyMenu.vendor.model.ProductDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryTest {
    @Autowired
    CategoryDataAccess categoryDataAccess;
    @Autowired
    RecipeDataAccess recipeAccessData;

    @Test
    public void testMapperDtoToEntity() {
        InventoryMapper inventoryMapper = InventoryMapper.INSTANCE;
        CategoryDto categoryDTO = new CategoryDto();
        categoryDTO.setId(UUID.randomUUID().toString());

        System.out.println(inventoryMapper.categoryDtoToCategory(categoryDTO));

        ProductDto productDTO = new ProductDto();
        productDTO.setName("Sugar");
        productDTO.setCatId(categoryDTO.getId());
        System.out.println("&&&&& --> #################");
        System.out.println(inventoryMapper.productDtoToProduct(productDTO));
    }

    @Test
    public void testMapperEntityToDto() {
        InventoryMapper inventoryMapper = InventoryMapper.INSTANCE;
        ProductDB product = new ProductDB();
        product.setName("Sugar");
        product.setId("4321");
        product.setQuantityType("33");

        CategoryDB category = new CategoryDB();
        category.setId("1234");
        product.setCategory(category);

        System.out.println("&&&&& --> #################");
        System.out.println(inventoryMapper.productToProductDto(product));
    }
}
