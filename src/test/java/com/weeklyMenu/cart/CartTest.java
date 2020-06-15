package com.weeklyMenu.cart;

import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.ProductItemDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.dataAccess.CartAccessDataImpl;
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
public class CartTest {

    @Autowired
    CartAccessDataImpl cartAccessData;

    @Test
    public void simpleCrud() {
        CartDto cartDto = new CartDto();
        cartDto.setName("Today");

        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recDto = new RecipeDto();
        recDto.setId("REC_ID_01");
        recipes.add(recDto);

        List<ProductItemDto> itemsDto = new ArrayList<>();
        ProductItemDto itemDto = new ProductItemDto();
        itemDto.setProdId("PROD_01");
        itemDto.setRecipes(recipes);
        itemsDto.add(itemDto);

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
