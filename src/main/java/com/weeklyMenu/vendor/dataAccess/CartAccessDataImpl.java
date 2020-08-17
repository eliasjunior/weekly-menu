package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.domain.data.CartDataAccess;
import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.CartItemDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.model.Cart;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
            dto.setProductItems(generateIdProdItem(dto.getProductItems()));
        }
        Cart cartNew = saveCart(dto);
        CartDto cartDto = MAPPER.cartToDto(cartNew);
        cartDto.setProductItems(MAPPER.cartItemsToCartItemsDtos(cartNew.getCartItems()));
        return cartDto;
    }

    @Override
    public void update(CartDto dto) {
        LOGGER.debug("update", dto.toString());
        this.validator.validateCartDto(dto);
        dto.setProductItems(generateIdProdItem(dto.getProductItems()));
        Optional<Cart> optional = repository.findById(dto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Shopping list not found to update");
        }

        saveCart(dto);
    }

    private Cart saveCart(CartDto dto) {
        Cart cart = MAPPER.dtoToCart(dto);
        cart.setCartItems(MAPPER.cartItemsDtosToCartItems(dto.getProductItems()));
        cart.getCartItems().forEach(cartItem -> {
            // if remove the validation it will save only with the ID, maybe try to use more the constraints to validate ?
            cartItem.setRecipes(fillRecipe(cartItem.getRecipes(), cartItem));
            cartItem.setProduct(fillProduct(cartItem.getProduct()));
            cartItem.setCart(cart);
        });

        return repository.save(cart);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    private List<CartItemDto> generateIdProdItem(List<CartItemDto> dtoItems) {
        if (dtoItems == null) {
            throw new CustomValidationException("There is not product item to save the cart");
        }
        return dtoItems
                .stream()
                .map(productItemDto -> {
                    if(productItemDto.getId() == null) {
                        productItemDto.setId(idGenerator.generateId());
                    }
                    return productItemDto;
                })
                .collect(Collectors.toList());
    }

    private List<Recipe> fillRecipe(List<Recipe> recipes, CartItem cartItem) {
        if(Objects.isNull(recipes)) {
            return null;
        }
        return recipes.stream().map(recipe -> {
            Optional<Recipe> optional = recipeRepository.findById(recipe.getId());
            if (optional.isPresent()) {
                Recipe recIn = optional.get();
                recIn.setCartItem(cartItem);
                return recIn;
            } else {
                String msgError = "Attempt to save cartItem but recipe does not exist, id=" + recipe.getId();
                throw new CustomValidationException(msgError);
            }
        }).collect(Collectors.toList());
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
