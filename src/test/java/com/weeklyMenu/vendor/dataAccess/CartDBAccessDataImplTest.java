package com.weeklyMenu.webAdaptor.dataAccess;

import com.weeklyMenu.BaseIntegration;
import com.weeklyMenu.RecipeFactory;
import com.weeklyMenu.webAdaptor.mapper.CartItemMapper;
import com.weeklyMenu.webAdaptor.mapper.CartMapper;
import com.weeklyMenu.webAdaptor.mapper.RecipeMapper;
import com.weeklyMenu.webAdaptor.model.CartDB;
import com.weeklyMenu.webAdaptor.model.CartItemDB;
import com.weeklyMenu.webAdaptor.repository.CartRepository;
import com.weeklyMenu.webAdaptor.repository.ProdDetailRepository;
import com.weeklyMenu.webAdaptor.repository.ProductRepository;
import com.weeklyMenu.webAdaptor.repository.RecipeRepository;
import main.java.com.weeklyMenu.entity.*;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.CartGateway;
import main.java.com.weeklyMenu.gateway.CategoryGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static com.weeklyMenu.RecipeFactory.createRecipeDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartDBAccessDataImplTest {
    @Autowired
    private CategoryGateway categoryDataAccess;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private CartGateway cartDataAccess;
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
        Cart cartDto = new Cart();
        com.weeklyMenu.webAdaptor.dataAccess.CartAccessDataImpl impl = new com.weeklyMenu.webAdaptor.dataAccess.CartAccessDataImpl(cartRepository);
        impl.create(cartDto);
    }

    @Test
    public void test_cartSave_provide_correct_data() {
        Product productDto = baseIntegration.createNewProduct();
        RecipeMapper recMapper = RecipeMapper.INSTANCE;

        List<ProdDetail> prodDetail = RecipeFactory.createSingleListProdDetailDto(productDto);
        prodDetail.forEach(prodDetailDto -> prodDetailRepository.save(recMapper.prodDetailToProdDetailDB(prodDetailDto)));

        Recipe recipeDtoJustCreated = recMapper.recipeDBToRecipe(recipeRepository.save(recMapper
                .recipeToRecipeDB(createRecipeDto(prodDetail))));

        Cart newCartDto = createCartWithRecipes(recipeDtoJustCreated, productDto);

        assertNotNull(newCartDto.getId());
        assertEquals(dummyNameCart, newCartDto.getName());
        assertEquals(1, newCartDto.getCartItems().size());
    }

    @Test
    public void test_cart_update_provide_correct_data() {
        RecipeMapper recMapper = RecipeMapper.INSTANCE;
        Product product = baseIntegration.createNewProduct();
        List<ProdDetail> prodDetailDtos = RecipeFactory.createSingleListProdDetailDto(product);
        prodDetailDtos.forEach(prodDetailDto -> prodDetailRepository.save(recMapper.prodDetailToProdDetailDB(prodDetailDto)));
        Recipe recipeDto = createRecipeDto(prodDetailDtos);

        Recipe recipeDtoNew = recMapper.recipeDBToRecipe(recipeRepository.save(recMapper.recipeToRecipeDB(recipeDto)));
        String newUpdatedName = "Name Changed here";
        Cart newCartDto = createCartWithRecipes(recipeDtoNew, product);
        newCartDto.setName(newUpdatedName);

        cartDataAccess.edit(newCartDto);

        CartDB cartUpdate = cartRepository.findById(newCartDto.getId()).get();

        assertEquals(newUpdatedName, cartUpdate.getName());
    }

    @Test(expected = CustomValidationException.class)
    public void test_cart_update_bad_data() {
        Cart newCartDto = new Cart();

        newCartDto.setName("No Muchacho");

        cartDataAccess.edit(newCartDto);

        CartDB cartUpdate = cartRepository.findById(newCartDto.getId()).get();

        assertEquals(cartUpdate.getName(), "No Muchacho");
    }

    @Test
    public void test_add_item_from_existing_cart() {
        RecipeMapper recMapper = RecipeMapper.INSTANCE;
        Product productDto = baseIntegration.createNewProduct();

        List<ProdDetail> prodDetailDtos = RecipeFactory.createSingleListProdDetailDto(productDto);
        prodDetailDtos.forEach(prodDetailDto -> prodDetailRepository.save(recMapper.prodDetailToProdDetailDB(prodDetailDto)));
        Recipe recipeDto = createRecipeDto(prodDetailDtos);

        Recipe recipeDtoNew = recMapper.recipeDBToRecipe(recipeRepository.save(recMapper.recipeToRecipeDB(recipeDto)));
        Cart newCartDto = createCartWithRecipes(recipeDtoNew, productDto);

        assertEquals(1, newCartDto.getCartItems().size());

        Product newProduct = baseIntegration.createNewProduct();

        List<CartItem> cartItems = newCartDto.getCartItems();

        cartItems.add(createCartItem(newProduct.getId(), null));

        newCartDto.setCartItems(cartItems);

        cartDataAccess.edit(newCartDto);

        Optional<CartDB> op = cartRepository.findById(newCartDto.getId());

        assertEquals(2, op.get().getCartItems().size());
    }

    @Test
    public void test_remove_item_from_existing_cart() {
        CartMapper MAPPER = CartMapper.INSTANCE;
        CartItemMapper cartItemMapper = new CartItemMapper();

        Product productDto1 = baseIntegration.createNewProduct("Water", UUID.randomUUID().toString());
        Product productDto2 = baseIntegration.createNewProduct("Orange Juice", UUID.randomUUID().toString());

        CartItem item1 = baseIntegration.cartItemDtoFactory(productDto1.getId(), null);
        item1.setId(UUID.randomUUID().toString());
        CartItem item2 = baseIntegration.cartItemDtoFactory(productDto2.getId(), null);
        item2.setId(UUID.randomUUID().toString());

        Cart cartDto = new Cart();
        cartDto.setId(UUID.randomUUID().toString());
        cartDto.setCartItems(Arrays.asList(item1, item2));
        cartDto.setName("Tomorrow");

        CartDB cart = MAPPER.cartToCartDB(cartDto);
        cart.setCartItems(cartItemMapper.cartItemsToCartItems(cartDto.getCartItems()));
        CartDB newCart = cartRepository.save(cart);

        newCart.getCartItems()
                .forEach(cartItem -> System.out.println(cartItem.getProduct().getName() + ": - " + cartItem.getProduct().getId()));

        List<CartItemDB> changedItems = newCart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productDto1.getId()))
                .collect(Collectors.toList());

        // update cart items
        newCart.setCartItems(changedItems);

        assertEquals(1, newCart.getCartItems().size());

        Cart cartRequestDTO = MAPPER.cartDBToCart(newCart);
        cartRequestDTO.setCartItems(cartItemMapper.cartItemsToCartItemDtos(newCart.getCartItems()));

        // from here is the test the above should had being tested in another test
        cartDataAccess.edit(cartRequestDTO);

        CartDB cartEditedDB = cartRepository.findById(cartRequestDTO.getId()).get();
        cartEditedDB.setCartItems(cartItemMapper.cartItemsToCartItems(cartRequestDTO.getCartItems()));
        assertEquals(1, cartEditedDB.getCartItems().size());
    }

    private Cart createCartWithRecipes(Recipe recipe, Product productDto) {
        Cart cartDto = new Cart();
        cartDto.setCartItems(createProductItem(productDto.getId(), recipe));
        cartDto.setName(dummyNameCart);
        cartDataAccess.edit(cartDto);
        CartMapper MAPPER = CartMapper.INSTANCE;
        return   MAPPER.cartDBToCart(cartRepository.findById(cartDto.getId()).get());
    }

    private List<CartItem> createProductItem(String prodId, Recipe recipeDto) {
        return baseIntegration.cartItemsFactory(prodId, new HashSet<>(Arrays.asList(recipeDto.getId())));
    }

    private CartItem createCartItem(String prodId, Set<String> recipes) {
        CartItem item = new CartItem();
        item.setProdId(prodId);
        if (recipes != null) {
            item.setRecipes(recipes);
        }
        return item;
    }
}