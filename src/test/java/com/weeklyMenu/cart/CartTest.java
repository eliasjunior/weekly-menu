package com.weeklyMenu.cart;

import com.weeklyMenu.BaseIntegration;
import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.ProductItemDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.dataAccess.CartAccessDataImpl;
import com.weeklyMenu.vendor.dataAccess.CategoryDataAccessImpl;
import com.weeklyMenu.vendor.dataAccess.ProductDataAccessImpl;
import com.weeklyMenu.vendor.dataAccess.RecipeAccessDataImpl;
import com.weeklyMenu.vendor.model.ProdDetail;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.repository.CategoryRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartTest extends BaseIntegration {

    @Autowired
    CartAccessDataImpl cartAccessData;
    @Autowired
    RecipeAccessDataImpl recipeAccessData;
    @Autowired
    private CategoryDataAccessImpl catApiData;
    @Autowired
    private ProductDataAccessImpl productDataAccess;

    @Test
    public void basicCreation() {
        ProductDto productDto = createProduct(catApiData, productDataAccess);

        CartDto cartDto = new CartDto();
        cartDto.setName("Today");

        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recDto = new RecipeDto();
        recipes.add(recDto);

        List<ProductItemDto> itemsDto = new ArrayList<>();

        ProductItemDto itemDto = new ProductItemDto();
        itemDto.setProdId(productDto.getId());
        itemDto.setRecipes(recipes);

        itemsDto.add(itemDto);

        recipes.forEach(recipeDto -> {
            ProdDetail prodDetail = new ProdDetail();
            prodDetail.setProdId(productDto.getId());
            prodDetail.setQuantity(1);

            List<ProdDetailDto> listProds = new ArrayList<>();
            recipeDto.setProdsDetail(listProds);

            recipeAccessData.save(recipeDto);
        });

        cartDto.setProductItems(itemsDto);

        cartAccessData.save(cartDto);

        List<CartDto> dtoList = cartAccessData.getCartList();
        dtoList.forEach(shoppingItem -> {
            System.out.println("-->" + shoppingItem.getName());
            System.out.println("################# items" + shoppingItem.getProductItems());
            for (ProductItemDto productItem : shoppingItem.getProductItems()) {
                System.out.println(productItem.getProdId());
                productItem.getRecipes().forEach(System.out::println);
            }
        });

        assertEquals(dtoList.get(0).getProductItems().size(), 1);
    }
}
