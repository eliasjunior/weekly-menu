package com.weeklyMenu.cart;

import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.model.CartItemDB;
import com.weeklyMenu.vendor.model.ProductDB;
import com.weeklyMenu.vendor.model.RecipeDB;
import com.weeklyMenu.vendor.model.CartDB;
import main.java.com.weeklyMenu.entity.Cart;
import main.java.com.weeklyMenu.entity.CartItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CartDBMapperTest {
    CartMapper CART_MAPPER = CartMapper.INSTANCE;
    @Test
    public void testMapperDtoToEntity() {
        CartMapper MAPPER = CartMapper.INSTANCE;

        Cart domain = new Cart();
        domain.setName("Today");
        domain.setId(UUID.randomUUID().toString());

        Set<String> recipes = new HashSet<>();
        String rec1 = "REC_ID_01";
        recipes.add(rec1);

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItemDTO = new CartItem();
        cartItemDTO.setProdId("PROD_01");
        cartItemDTO.setRecipes(recipes);
        cartItems.add(cartItemDTO);

        domain.setCartItems(cartItems);

        System.out.println("Cart #################, Items qtd="+domain.getCartItems().size());
        CartDB cartDB = MAPPER.dtoToCart(domain);

        System.out.println(cartDB);
        cartDB.getCartItemDBS().forEach(item -> System.out.println(item));

        List<CartItem> cartItemDtos = domain.getCartItems();
        List<CartItemDB> cartItemDBS = cartDB.getCartItemDBS();

        assertEquals(cartDB.getId(), cartDB.getId());
        assertEquals(cartDB.getName(), cartDB.getName());
        assertEquals(cartItemDtos.get(0).getProdId(), "PROD_01");
        assertEquals(cartItemDtos.get(0).getId(), cartItemDBS.get(0).getId());
    }

    @Test
    public void testMapperEntityToDto() {
        CartDB cartDB = new CartDB();
        cartDB.setName("Today");
        cartDB.setId(UUID.randomUUID().toString());

        ProductDB product = new ProductDB();
        product.setName("Orange");
        product.setId("PROD_01");

        RecipeDB recipe = new RecipeDB();
        recipe.setId("rec1");

        Cart cart = CART_MAPPER.cartToDto(cartDB);

        assertEquals(cart.getId(), cart.getId());
        assertEquals(cart.getName(), cart.getName());
    }
}
