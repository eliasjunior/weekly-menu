package com.weeklyMenu.adaptor.data;

import com.weeklyMenu.adaptor.mapper.InventoryMapper;
import com.weeklyMenu.adaptor.model.CategoryDB;
import com.weeklyMenu.adaptor.model.ProductDB;
import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class InventoryTest {

    @Test
    public void testMapperDtoToEntity() {
        String catId = UUID.randomUUID().toString();
        InventoryMapper inventoryMapper = InventoryMapper.INSTANCE;
        Category category = Category.builder().build();
        category.setId(catId);
        CategoryDB categoryDB = inventoryMapper.categoryToCategoryDB(category);

        assertEquals(categoryDB.getId(), category.getId());

        Product product = new Product();
        product.setName("Sugar");
        product.setCatId(category.getId());

        ProductDB productDB = inventoryMapper.productToProductDB(product);
        assertEquals(productDB.getName(), "Sugar");
        assertEquals(productDB.getCategory().getId(), product.getCatId());
    }

    @Test
    public void testMapperEntityToDto() {
        InventoryMapper inventoryMapper = InventoryMapper.INSTANCE;
        ProductDB productDB = new ProductDB();
        productDB.setName("Sugar");
        productDB.setId("4321");
        productDB.setQuantityType("33");

        CategoryDB categoryDB = new CategoryDB();
        categoryDB.setId("1234");
        productDB.setCategory(categoryDB);

        Product product1 = inventoryMapper.productDBToProduct(productDB);
        assertEquals(product1.getName(), "Sugar");
        assertEquals(product1.getId(), "4321");
        assertEquals(product1.getQuantityType(), "33");
        assertEquals(product1.getCatId(), categoryDB.getId());
    }
}
