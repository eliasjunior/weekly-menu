package com.weeklyMenu.cart;

import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.model.Cart;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CartMapperTest {
    CartMapper CART_MAPPER = CartMapper.INSTANCE;
    @Test
    public void testMapperDtoToEntity() {
        CartMapper MAPPER = CartMapper.INSTANCE;

        CartDto cartDto = new CartDto();
        cartDto.setName("Today");
        cartDto.setId(UUID.randomUUID().toString());

        Set<String> recipes = new HashSet<>();
        String rec1 = "REC_ID_01";
        recipes.add(rec1);

        List<CartItemDto> cartItemsDTO = new ArrayList<>();
        CartItemDto cartItemDTO = new CartItemDto();
        cartItemDTO.setProdId("PROD_01");
        cartItemDTO.setRecipes(recipes);
        cartItemsDTO.add(cartItemDTO);

        cartDto.setCartItems(cartItemsDTO);

        System.out.println("Cart #################, Items qtd="+cartDto.getCartItems().size());
        Cart cart = MAPPER.dtoToCart(cartDto);

       // cart.setCartItems(MAPPER.cartItemsDtosToCartItems(cartDto.getCartItems()));

        System.out.println(cart);
        cart.getCartItems().forEach(item -> System.out.println(item));

        List<CartItemDto> cartItemDtos = cartDto.getCartItems();
        List<CartItem> cartItems = cart.getCartItems();

        assertEquals(cartDto.getId(), cart.getId());
        assertEquals(cartDto.getName(), cart.getName());
        assertEquals(cartItemDtos.get(0).getProdId(), "PROD_01");
        assertEquals(cartItemDtos.get(0).getId(), cartItems.get(0).getId());
    }

    @Test
    public void testMapperEntityToDto() {
        Cart cart = new Cart();
        cart.setName("Today");
        cart.setId(UUID.randomUUID().toString());

        Product product = new Product();
        product.setName("Orange");
        product.setId("PROD_01");

        Recipe recipe = new Recipe();
        recipe.setId("rec1");

        CartDto cartDto = CART_MAPPER.cartToDto(cart);

        assertEquals(cart.getId(), cartDto.getId());
        assertEquals(cart.getName(), cartDto.getName());
    }
}
