package com.weeklyMenu.inventory;

import com.weeklyMenu.dto.CategoryDTO;
import com.weeklyMenu.dto.ProductDTO;
import com.weeklyMenu.vendor.mapper.InventoryMapper;
import com.weeklyMenu.vendor.dataAccess.CategoryAccessImpl;
import com.weeklyMenu.vendor.dataAccess.ProductAccessImpl;
import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.repository.CategoryRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
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
    private ProductAccessImpl prodApiData;
    @Autowired
    private CategoryAccessImpl catApiData;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testMapperDtoToEntity() {
        InventoryMapper inventoryMapper = InventoryMapper.INSTANCE;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(UUID.randomUUID().toString());

        System.out.println(inventoryMapper.categoryDtoToCategory(categoryDTO));

        ProductDTO productDTO = new ProductDTO();
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

    @Test
    public void simpleCrudInventory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Fruits");
        catApiData.save(categoryDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("apple");
        //  productDTO.setCategoryDTO(categoryDTO);

        ProductDTO newProdDto = prodApiData.save(productDTO);

        System.out.println("PROD --> #################");
        for (Product product : productRepository.findAll()) {
            System.out.println(product.getName());
            //   System.out.println(product.getCategory());
        }
        System.out.println("CAT --> #################");
        for (Category category : categoryRepository.findAll()) {
            System.out.println(category.getName());
            category.getProducts().forEach(product -> {
                System.out.println("CHILDREN --> #################");
                System.out.println(product.getName());
            });
        }
    }
}
