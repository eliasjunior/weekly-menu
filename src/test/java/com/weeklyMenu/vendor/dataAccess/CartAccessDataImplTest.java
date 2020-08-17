package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.BaseIntegration;
import com.weeklyMenu.RecipeFactory;
import com.weeklyMenu.domain.data.CartDataAccess;
import com.weeklyMenu.domain.data.CategoryDataAccess;
import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.domain.data.RecipeDataAccess;
import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.Cart;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.weeklyMenu.RecipeFactory.createRecipe;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartAccessDataImplTest {
    @Autowired
    private CategoryDataAccess categoryDataAccess;
    @Autowired
    private ProductDataAccess productDataAccess;
    @Autowired
    private RecipeDataAccess recipeDataAccess;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private CartDataAccess cartDataAccess;

    BaseIntegration baseIntegration;

    @Before
    public void setUp() {
        baseIntegration = new BaseIntegration(categoryDataAccess, productDataAccess, recipeRepository);
    }

    @Test(expected = CustomValidationException.class)
    public void test_whenCartDtoPostIsNotValid_throwCustomException() {

        CartDto cartDto = new CartDto();
        CategoryDto categoryDto = baseIntegration.categoryFactory();
        ProductDto productDto = baseIntegration.productFactory(categoryDto.getId());

        DataAccessValidator validator = new DataAccessValidator();
        IdGenerator idGenerator = new IdGenerator() {
            @Override
            public String generateId() {
                return "someIdGenerated";
            }
        };
        CartAccessDataImpl impl = new CartAccessDataImpl(cartRepository, recipeRepository, productRepository, idGenerator, validator);
        impl.save(cartDto);

    }

    @Test
    public void test_cartSave_provide_correct_data() {
        CartDto newCartDto = createCart();
        CartDto cartDtoSaved = cartDataAccess.save(newCartDto);

        assertNotNull(cartDtoSaved.getId());
        assertEquals("Today Vamos", cartDtoSaved.getName());
        assertEquals(1, cartDtoSaved.getProductItems().size());
    }

    @Test
    public void test_cart_update_provide_correct_data() {
        CartDto newCartDto = createCart();
        newCartDto.setName("No Muchacho");

        cartDataAccess.update(newCartDto);

        Cart cartUpdate = cartRepository.findById(newCartDto.getId()).get();

        assertEquals("No Muchacho", cartUpdate.getName());
    }

    @Test(expected = CustomValidationException.class)
    public void test_cart_update_bad_data() {
        CartDto newCartDto = new CartDto();

        newCartDto.setName("No Muchacho");

        cartDataAccess.update(newCartDto);

        Cart cartUpdate = cartRepository.findById(newCartDto.getId()).get();

        assertEquals(cartUpdate.getName(), "No Muchacho");
    }

    @Test
    public void test_add_item_from_existing_cart() {
        CartDto newCartDto = createCart();

        assertEquals(1, newCartDto.getProductItems().size());

        ProductDto newProduct = baseIntegration.createNewProduct("orange");

        List<CartItemDto> cartItems = newCartDto.getProductItems();

        cartItems.add(createCartItem(newProduct.getId(), null));

        newCartDto.setProductItems(cartItems);

        cartDataAccess.update(newCartDto);

        Cart cartUpdate = cartRepository.findById(newCartDto.getId()).get();

        assertEquals(2, cartUpdate.getCartItems().size());
    }

    @Test
    public void test_remove_item_from_existing_cart() {
        CartMapper MAPPER = CartMapper.INSTANCE;

        ProductDto productDto1 = baseIntegration.createNewProduct("Water");
        ProductDto productDto2 = baseIntegration.createNewProduct("Orange Juice");

        CartItemDto item1 = baseIntegration.cartItemFactory(productDto1.getId(), null);
        item1.setId(UUID.randomUUID().toString());
        CartItemDto item2 = baseIntegration.cartItemFactory(productDto2.getId(), null);
        item2.setId(UUID.randomUUID().toString());

        CartDto cartDto = new CartDto();
        cartDto.setId(UUID.randomUUID().toString());
        cartDto.setProductItems(Arrays.asList(item1, item2));
        cartDto.setName("Tomorrow");

        Cart cart = MAPPER.dtoToCart(cartDto);
        cart.setCartItems(MAPPER.cartItemsDtosToCartItems(cartDto.getProductItems()));
        Cart newCart = cartRepository.save(cart);

        List<CartItem> changedItems = newCart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId() == productDto1.getId())
                .collect(Collectors.toList());


        newCart.setCartItems(changedItems);

        CartDto cartRequestDTO = MAPPER.cartToDto(newCart);
        cartRequestDTO.setProductItems(MAPPER.cartItemsToCartItemsDtos(newCart.getCartItems()));

        assertEquals(1, cartRequestDTO.getProductItems().size());

        // from here is the test the above should had being tested in another test
        cartDataAccess.update(cartRequestDTO);

        Cart cartEditedDB = cartRepository.findById(cartRequestDTO.getId()).get();
        assertEquals(1, cartEditedDB.getCartItems().size());
    }

    private CartDto createCart() {
        CartDto cartDto = new CartDto();
        ProductDto productDto = baseIntegration.createNewProduct();

        RecipeDto recipeDto = createRecipe(RecipeFactory.createSingleList(productDto));
        Recipe recipe = recipeRepository.save(RecipeMapper.INSTANCE.recipeDtoToRecipe(recipeDto));

        cartDto.setProductItems(createProductItem(productDto.getId(), RecipeMapper.INSTANCE.recipeToRecipeDto(recipe)));

        cartDto.setName("Today Vamos");

        return cartDataAccess.save(cartDto);
    }

    private List<CartItemDto> createProductItem(String prodId, RecipeDto recipeDto) {
        return baseIntegration.cartItemsFactory(prodId, Arrays.asList(recipeDto));
    }

    private CartItemDto createCartItem(String prodId, List<RecipeDto> recipes) {
        CartItemDto item = new CartItemDto();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return item;
    }
}