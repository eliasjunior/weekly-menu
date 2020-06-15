package com.weeklyMenu.cart;

import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.ProductItemDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.model.ProductItem;
import com.weeklyMenu.vendor.model.Cart;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartMapperTest {

    @Test
    public void testMapperDtoToEntity() {
        CartMapper MAPPER = CartMapper.INSTANCE;

        CartDto cartDto = new CartDto();
        cartDto.setName("Today");
        cartDto.setId(UUID.randomUUID().toString());

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

        System.out.println("Cart #################");
        Cart cart = MAPPER.dtoToCart(cartDto);
        System.out.println(cart);
        cart.getProductItems().forEach(item -> System.out.println(item));
    }

    @Test
    public void testMapperEntityToDto() {
        CartMapper MAPPER = CartMapper.INSTANCE;

        Cart cart = new Cart();
        cart.setName("Today");
        cart.setId(UUID.randomUUID().toString());

        List<Recipe> recipes = new ArrayList<>();
        Recipe rec = new Recipe();
        rec.setId("REC_01");
        recipes.add(rec);

        List<ProductItem> items = new ArrayList<>();
        ProductItem item = new ProductItem();
        Product product = new Product();
        product.setId("PROD_01");
        item.setProduct(product);
        item.setRecipes(recipes);
        items.add(item);

        cart.setProductItems(items);

        System.out.println("cart #################");
        System.out.println(MAPPER.cartToDto(cart));
    }
}
