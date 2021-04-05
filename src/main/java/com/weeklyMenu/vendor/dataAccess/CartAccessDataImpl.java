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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartAccessDataImpl implements CartDataAccess {
    final Logger LOGGER = LoggerFactory.getLogger(CartDataAccess.class);
    private final CartRepository cartRepository;
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private final CartMapper MAPPER = CartMapper.INSTANCE;
    private IdGenerator idGenerator;
    private DataAccessValidator validator;
    private Map<String, Recipe> recipeLookup;
    CartItemMapper cartItemMapper = new CartItemMapper();

    public CartAccessDataImpl(CartRepository cartRepository,
                              RecipeRepository recipeRepository,
                              ProductRepository productRepository,
                              IdGenerator idGenerator, DataAccessValidator validator) {
        this.cartRepository = cartRepository;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
        this.idGenerator = idGenerator;
        this.validator = validator;
    }

    @Override
    public List<CartDto> getCartList() {
        List<Cart> carts = cartRepository.findAllActives();
        List<CartDto> cartsDto = MAPPER.cartsToCartDtos(carts);
        int i = 0;
        for (Cart cart : carts) {
            CartDto cartDto = cartsDto.get(i);
            cartDto.setCartItems(cartItemMapper.cartItemsToCartItemDtos(cart.getCartItems()));
            i++;
        }
        return cartsDto;
    }

    @Override
    public CartDto save(CartDto dto) {
        LOGGER.debug("--- save --- " + dto.toString());
        this.validator.validateCartDto(dto);
        if (dto.getId() == null) {
            dto.setId(idGenerator.generateId());
            dto.setCartItems(generateIdProdItem(dto.getCartItems()));
        }
        return saveCart(dto, null);
    }

    @Override
    public CartDto update(CartDto dto) {
        LOGGER.debug("--- update --- " + dto.toString());
        this.validator.validateCartDto(dto);
        dto.setCartItems(generateIdProdItem(dto.getCartItems()));
        Optional<Cart> optional = cartRepository.findById(dto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Update failed because the cart id sent by the request was not found!");
        }
        Cart inBdCart = optional.get();
        // validate if cartItem has already the product
        for (CartItemDto itemDto : dto.getCartItems()) {
            if(!containsItem(inBdCart.getCartItems(), itemDto.getId())) {
                if(containsProdInCart(inBdCart.getCartItems(), itemDto.getProdId())) {
                    throw new CustomValidationException("Attempt to update the cart has failed because the cart already has a product! and cart item is new");
                }
            }
        }
        return saveCart(dto, inBdCart);
    }

    @Override
    public void delete(String id) {
        cartRepository.deleteById(id);
    }

    private List<CartItemDto> generateIdProdItem(List<CartItemDto> dtoItems) {
        if (Objects.isNull(dtoItems)) {
            throw new CustomValidationException("There is not product item to save the cart");
        }
        return dtoItems
                .stream()
                .peek(cartItemDto -> {
                    if (Objects.isNull(cartItemDto.getId())) {
                        cartItemDto.setId(idGenerator.generateId());
                    }
                })
                .collect(Collectors.toList());
    }

    private CartDto saveCart(CartDto dto, Cart cartUpdate) {
        dto.getCartItems().forEach(cartItemDto ->  {
            validateRecipes(cartItemDto.getRecipes());
            validateProduct(cartItemDto.getProdId());
        });

        Cart cart = MAPPER.dtoToCart(dto);

        cart.setCartItems(cartItemMapper.cartItemDtosToCartItems(dto.getCartItems()));

        if(cartUpdate != null) {
            cart.linkItemsToCart(cartUpdate.getBasicEntity(), cartUpdate.getCartItems());
            cartUpdate.removeAllItems();
        } else {
            cart.linkItemsToCart(null);
        }

        Cart cartNew =  cartRepository.save(cart);
        CartDto cartDto = MAPPER.cartToDto(cartNew);
        cartDto.setCartItems(cartItemMapper.cartItemsToCartItemDtos(cartNew.getCartItems()));

        return cartDto;
    }

    private boolean containsItem(final List<CartItem> list, final String id){
        return list
                .stream()
                .anyMatch(cartItem -> cartItem.getId().equals(id));
    }

    private boolean containsProdInCart(final List<CartItem> list, final String prodId){
        return list
                .stream()
                .anyMatch(cartItem -> cartItem.getProduct().getId().equals(prodId));
    }

    private void validateRecipes(Set<String> recipes) {
        if (Objects.nonNull(recipes)) {
            if (Objects.isNull(recipeLookup)) {
                recipeLookup = new HashMap<>();
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

    private void validateProduct(String prodId) {
        Optional<Product> prodIn = productRepository.findById(prodId);
        if (!prodIn.isPresent()) {
            String msgError = "Attempt to save cartItem but product does not, exist id=" + prodId;
            throw new CustomValidationException(msgError);
        }
    }
}
