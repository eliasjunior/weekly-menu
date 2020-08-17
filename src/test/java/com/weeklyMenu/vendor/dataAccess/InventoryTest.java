package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.BaseIntegration;
import com.weeklyMenu.domain.data.CategoryDataAccess;
import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.domain.data.RecipeDataAccess;
import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.vendor.dataAccess.CategoryDataAccessImpl;
import com.weeklyMenu.vendor.dataAccess.ProductDataAccessImpl;
import com.weeklyMenu.vendor.mapper.InventoryMapper;
import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.repository.RecipeRepository;
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
    @Autowired
    private ProductDataAccess productDataAccess;
    @Autowired
    private RecipeRepository recipeRepository;

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
        Product product = new Product();
        product.setName("Sugar");
        product.setId("4321");
        product.setQuantityType("33");

        Category category = new Category();
        category.setId("1234");
        product.setCategory(category);

        System.out.println("&&&&& --> #################");
        System.out.println(inventoryMapper.productToProductDto(product));
    }
}
