package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.BaseIntegration;
import com.weeklyMenu.RecipeFactory;
import com.weeklyMenu.domain.data.CartDataAccess;
import com.weeklyMenu.domain.data.CategoryDataAccess;
import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.mapper.CartItemMapper;
import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.Cart;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.ProdDetailRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.weeklyMenu.RecipeFactory.createRecipeDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartAccessDataImplTest {
    @Autowired
    private CategoryDataAccess categoryDataAccess;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private CartDataAccess cartDataAccess;
    @Autowired
    private ProdDetailRepository prodDetailRepository;

    private final String dummyNameCart = "Easy list";

    BaseIntegration baseIntegration;

    @Before
    public void setUp() {
        baseIntegration = new BaseIntegration(categoryDataAccess, recipeRepository, productRepository);
    }

    @Test(expected = CustomValidationException.class)
    public void test_whenCartDtoPostIsNotValid_throwCustomException() {
        CartDto cartDto = new CartDto();
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
        ProductDto productDto = baseIntegration.createNewProduct();
        RecipeMapper recMapper = RecipeMapper.INSTANCE;

        List<ProdDetailDto> prodDetailDtos = RecipeFactory.createSingleListProdDetailDto(productDto);
        prodDetailDtos.stream().forEach(prodDetailDto -> {
            prodDetailRepository.save(recMapper.prodDetailDtoToProdDetail(prodDetailDto));
        });

        RecipeDto recipeDtoJustCreated = recMapper.recipeToRecipeDto(recipeRepository.save(recMapper
                .recipeDtoToRecipe(createRecipeDto(prodDetailDtos))));

        CartDto newCartDto = createCartWithRecipes(recipeDtoJustCreated, productDto);

        assertNotNull(newCartDto.getId());
        assertEquals(dummyNameCart, newCartDto.getName());
        assertEquals(1, newCartDto.getCartItems().size());
    }

    @Test
    public void test_cart_update_provide_correct_data() {
        RecipeMapper recMapper = RecipeMapper.INSTANCE;
        ProductDto productDto = baseIntegration.createNewProduct();
        List<ProdDetailDto> prodDetailDtos = RecipeFactory.createSingleListProdDetailDto(productDto);
        prodDetailDtos.stream().forEach(prodDetailDto -> {
            prodDetailRepository.save(recMapper.prodDetailDtoToProdDetail(prodDetailDto));
        });
        RecipeDto recipeDto = createRecipeDto(prodDetailDtos);

        RecipeDto recipeDtoNew = recMapper.recipeToRecipeDto(recipeRepository.save(recMapper.recipeDtoToRecipe(recipeDto)));
        String newUpdatedName = "Name Changed here";
        CartDto newCartDto = createCartWithRecipes(recipeDtoNew, productDto);
        newCartDto.setName(newUpdatedName);

        cartDataAccess.update(newCartDto);

        Cart cartUpdate = cartRepository.findById(newCartDto.getId()).get();

        assertEquals(newUpdatedName, cartUpdate.getName());
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
        RecipeMapper recMapper = RecipeMapper.INSTANCE;
        ProductDto productDto = baseIntegration.createNewProduct();

        List<ProdDetailDto> prodDetailDtos = RecipeFactory.createSingleListProdDetailDto(productDto);
        prodDetailDtos.stream().forEach(prodDetailDto -> {
            prodDetailRepository.save(recMapper.prodDetailDtoToProdDetail(prodDetailDto));
        });
        RecipeDto recipeDto = createRecipeDto(prodDetailDtos);

        RecipeDto recipeDtoNew = recMapper.recipeToRecipeDto(recipeRepository.save(recMapper.recipeDtoToRecipe(recipeDto)));
        CartDto newCartDto = createCartWithRecipes(recipeDtoNew, productDto);

        assertEquals(1, newCartDto.getCartItems().size());

        ProductDto newProduct = baseIntegration.createNewProduct();

        List<CartItemDto> cartItems = newCartDto.getCartItems();

        cartItems.add(createCartItem(newProduct.getId(), null));

        newCartDto.setCartItems(cartItems);

        CartDto actualTest = cartDataAccess.update(newCartDto);

        assertEquals(2, actualTest.getCartItems().size());
    }

    @Test
    public void test_remove_item_from_existing_cart() {
        CartMapper MAPPER = CartMapper.INSTANCE;
        CartItemMapper cartItemMapper = new CartItemMapper();

        ProductDto productDto1 = baseIntegration.createNewProduct("Water", UUID.randomUUID().toString());
        ProductDto productDto2 = baseIntegration.createNewProduct("Orange Juice", UUID.randomUUID().toString());

        CartItemDto item1 = baseIntegration.cartItemDtoFactory(productDto1.getId(), null);
        item1.setId(UUID.randomUUID().toString());
        CartItemDto item2 = baseIntegration.cartItemDtoFactory(productDto2.getId(), null);
        item2.setId(UUID.randomUUID().toString());

        CartDto cartDto = new CartDto();
        cartDto.setId(UUID.randomUUID().toString());
        cartDto.setCartItems(Arrays.asList(item1, item2));
        cartDto.setName("Tomorrow");

        Cart cart = MAPPER.dtoToCart(cartDto);
        cart.setCartItems(cartItemMapper.cartItemDtosToCartItems(cartDto.getCartItems()));
        Cart newCart = cartRepository.save(cart);

        newCart.getCartItems()
                .stream()
                .forEach(cartItem -> System.out.println(cartItem.getProduct().getName() + ": - " + cartItem.getProduct().getId()));

        List<CartItem> changedItems = newCart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId() == productDto1.getId())
                .collect(Collectors.toList());

        // update cart items
        newCart.setCartItems(changedItems);

        assertEquals(1, newCart.getCartItems().size());

        CartDto cartRequestDTO = MAPPER.cartToDto(newCart);
        cartRequestDTO.setCartItems(cartItemMapper.cartItemsToCartItemDtos(newCart.getCartItems()));

        // from here is the test the above should had being tested in another test
        cartDataAccess.update(cartRequestDTO);

        Cart cartEditedDB = cartRepository.findById(cartRequestDTO.getId()).get();
        cartEditedDB.setCartItems(cartItemMapper.cartItemDtosToCartItems(cartRequestDTO.getCartItems()));
        assertEquals(1, cartEditedDB.getCartItems().size());
    }

    private CartDto createCartWithRecipes(RecipeDto recipe, ProductDto productDto) {
        CartDto cartDto = new CartDto();
        cartDto.setCartItems(createProductItem(productDto.getId(), recipe));
        cartDto.setName(dummyNameCart);
        return cartDataAccess.save(cartDto);
    }

    private List<CartItemDto> createProductItem(String prodId, RecipeDto recipeDto) {
        return baseIntegration.cartItemsFactory(prodId, new HashSet<>(Arrays.asList(recipeDto.getId())));
    }

    private CartItemDto createCartItem(String prodId, Set<String> recipes) {
        CartItemDto item = new CartItemDto();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return item;
    }
}