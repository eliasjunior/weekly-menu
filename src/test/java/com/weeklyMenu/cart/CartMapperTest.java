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
import java.util.List;
import java.util.UUID;

public class CartMapperTest {
    CartMapper CART_MAPPER = CartMapper.INSTANCE;
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

        //TODO cartItems
        List<CartItemDto> cartItemsDTO = new ArrayList<>();
        CartItemDto cartItemDTO = new CartItemDto();
        cartItemDTO.setProdId("PROD_01");
        cartItemDTO.setRecipes(recipes);
        cartItemsDTO.add(cartItemDTO);

        cartDto.setProductItems(cartItemsDTO);

        System.out.println("Cart #################, Items qtd="+cartDto.getProductItems().size());
        Cart cart = MAPPER.dtoToCart(cartDto);

        cart.setCartItems(MAPPER.cartItemsDtosToCartItems(cartDto.getProductItems()));

        System.out.println(cart);
        cart.getCartItems().forEach(item -> System.out.println(item));
    }

    @Test
    public void testMapperEntityToDto() {
        Cart cart = new Cart();
        cart.setName("Today");
        cart.setId(UUID.randomUUID().toString());

        List<Recipe> recipes = new ArrayList<>();
        Recipe rec = new Recipe();
        rec.setId("REC_01");
        recipes.add(rec);

        List<CartItem> items = new ArrayList<>();
        CartItem item = new CartItem();
        Product product = new Product();
        product.setId("PROD_01");
        item.setProduct(product);
        item.setRecipes(recipes);
        items.add(item);

        cart.setCartItems(items);

        System.out.println("cart #################");
        System.out.println(CART_MAPPER.cartToDto(cart));
    }
}
