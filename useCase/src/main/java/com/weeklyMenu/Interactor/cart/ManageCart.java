package main.java.com.weeklyMenu.Interactor.cart;

import main.java.com.weeklyMenu.Interactor.validator.CartValidator;
import main.java.com.weeklyMenu.Interactor.validator.ProductValidator;
import main.java.com.weeklyMenu.Interactor.validator.RecipeValidator;
import main.java.com.weeklyMenu.entity.Cart;
import main.java.com.weeklyMenu.entity.CartItem;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.CartGateway;
import main.java.com.weeklyMenu.generator.IdGenerator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageCart {
    private final CartGateway cartGateway;
    private final CartValidator cartValidator;
    private final RecipeValidator recipeValidator;
    private final ProductValidator productValidator;
    private final IdGenerator idGenerator;

    public ManageCart(CartGateway cartGateway, CartValidator cartValidator,
                      RecipeValidator recipeValidator, ProductValidator productValidator,
                      IdGenerator idGenerator) {
        this.cartGateway = cartGateway;
        this.cartValidator = cartValidator;
        this.recipeValidator = recipeValidator;
        this.productValidator = productValidator;
        this.idGenerator = idGenerator;
    }

    public Cart create(Cart cart) {
        this.cartValidator.validateCart(cart);
        if (cart.getId() == null) {
            cart.setId(idGenerator.generateId());
            cart.setCartItems(generateIdProdItem(cart.getCartItems()));
        }
        cart.getCartItems().forEach(cartItemDto -> {
            recipeValidator.validateRecipes(cartItemDto.getRecipes());
            productValidator.validateProduct(cartItemDto.getProdId());
        });
        return this.cartGateway.create(cart);
    }

    public void edit(Cart cart) {
        cartValidator.validateCart(cart);

        cart.setCartItems(generateIdProdItem(cart.getCartItems()));
        cartValidator.validateCartAndProducts(findById(cart.getId()), cart);
        cart.getCartItems().forEach(cartItemDto -> {
            recipeValidator.validateRecipes(cartItemDto.getRecipes());
            productValidator.validateProduct(cartItemDto.getProdId());
        });
        this.cartGateway.edit(cart);
    }

    public void remove(String id) {
        cartGateway.remove(id);
    }

    public Cart findById(String id) {
        Optional<Cart> optional = cartGateway.findById(id);
        if (optional.isEmpty()) {
            throw new CustomValidationException("Attempt to retrieve cart has failed!");
        }
        return optional.get();
    }

    private List<CartItem> generateIdProdItem(List<CartItem> dtoItems) {
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
}
