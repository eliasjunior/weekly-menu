package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.domain.data.CartDataAccess;
import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.mapper.CartItemMapper;
import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.model.Cart;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartAccessDataImpl implements CartDataAccess {
    final Logger LOGGER = LoggerFactory.getLogger(CartDataAccess.class);
    private final CartRepository repository;
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private final CartMapper MAPPER = CartMapper.INSTANCE;
    private IdGenerator idGenerator;
    private DataAccessValidator validator;
    private Map<String, Recipe> recipeLookup;
    CartItemMapper cartItemMapper = new CartItemMapper();

    public CartAccessDataImpl(CartRepository repository,
                              RecipeRepository recipeRepository,
                              ProductRepository productRepository,
                              IdGenerator idGenerator, DataAccessValidator validator) {
        this.repository = repository;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
        this.idGenerator = idGenerator;
        this.validator = validator;
    }

    @Override
    public List<CartDto> getCartList() {
        List<Cart> listDto = repository.findAll();
        listDto.forEach(cart -> {
            System.out.println(cart.getCartItems());
        });
        return MAPPER.cartsToCartDtos(listDto);
    }

    @Override
    public CartDto save(CartDto dto) {
        LOGGER.debug("save", dto.toString());
        this.validator.validateCartDto(dto);
        if (dto.getId() == null) {
            dto.setId(idGenerator.generateId());
            dto.setCartItems(generateIdProdItem(dto.getCartItems()));
        }
        Cart cartNew = saveCart(dto);
        CartDto cartDto = MAPPER.cartToDto(cartNew);
        cartDto.setCartItems(cartItemMapper.cartItemsToCartItemDtos(cartNew.getCartItems()));
        return cartDto;
    }

    @Override
    public void update(CartDto dto) {
        LOGGER.debug("update", dto.toString());
        this.validator.validateCartDto(dto);
        dto.setCartItems(generateIdProdItem(dto.getCartItems()));
        Optional<Cart> optional = repository.findById(dto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Shopping list not found to update");
        }

        saveCart(dto);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    private List<CartItemDto> generateIdProdItem(List<CartItemDto> dtoItems) {
        if (Objects.isNull(dtoItems)) {
            throw new CustomValidationException("There is not product item to save the cart");
        }
        return dtoItems
                .stream()
                .map(productItemDto -> {
                    if (Objects.isNull(productItemDto.getId())) {
                        productItemDto.setId(idGenerator.generateId());
                    }
                    return productItemDto;
                })
                .collect(Collectors.toList());
    }

    private Cart saveCart(CartDto dto) {

        Cart cart = MAPPER.dtoToCart(dto);
        dto.getCartItems().forEach(cartItemDto -> validateRecipes(cartItemDto.getRecipes()));
        cart.setCartItems(cartItemMapper.cartItemDtosToCartItems(dto.getCartItems()));

        //TODO test if the converstion works
       // cart.setCartItems(ITEM_MAPPER.cartItemDtoToCartItem(dto.getCartItems()));
//        cart.getCartItems().forEach(cartItem -> {
//            // if remove the validation it will save only with the ID, maybe try to use more the constraints to validate ?
//            cartItem.setSelectedRecipes(validateRecipes());
//            cartItem.setProduct(fillProduct(cartItem.getProduct()));
//            cartItem.setCart(cart);
//        });
        Cart newCart = repository.save(cart);
        return newCart;
    }

    private void validateRecipes(Set<String> recipes) {
        if (Objects.nonNull(recipes)) {
            if (Objects.isNull(recipeLookup)) {
                recipeLookup = new HashMap();
            }
            for (String id : recipes) {
                Recipe recIn = recipeLookup.get(id);
                if (Objects.isNull(recIn)) {
                    Optional<Recipe> optional = recipeRepository.findById(id);
                    if (!optional.isPresent()) {
                        String msgError = "Attempt to save cartItem but recipe does not exist, id=" + id;
                        throw new CustomValidationException(msgError);
                    }
                }
            }
        }
    }

    private Product fillProduct(Product product) {
        Optional<Product> prodIn = productRepository.findById(product.getId());
        if (prodIn.isPresent()) {
            return prodIn.get();
        } else {
            String msgError = "Attempt to save cartItem but product does not, exist id=" + product.getId();
            throw new CustomValidationException(msgError);
        }
    }

}
