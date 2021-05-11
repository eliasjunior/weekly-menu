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
        cart.getCartItems().forEach(cartItemDto ->  {
            recipeValidator.validateRecipes(cartItemDto.getRecipes());
            productValidator.validateProduct(cartItemDto.getProdId());
        });
        return this.cartGateway.create(cart);
    }
    public void edit(Cart cart) {
        cartValidator.validateCart(cart);

        cart.setCartItems(generateIdProdItem(cart.getCartItems()));
        Optional<Cart> optional = cartGateway.findById(cart.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Update failed because the cart id sent by the request was not found!");
        }
        Cart oldCart = optional.get();
        // validate if cartItem has already the product
        for (CartItem itemDto : cart.getCartItems()) {
            if(!containsItem(oldCart.getCartItems(), itemDto.getId()) && containsProdInCart(oldCart.getCartItems(), itemDto.getProdId())) {
                throw new CustomValidationException("Attempt to update the cart has failed because the cart already has a product! and cart item is new");
            }
        }

        cart.getCartItems().forEach(cartItemDto ->  {
            recipeValidator.validateRecipes(cartItemDto.getRecipes());
            productValidator.validateProduct(cartItemDto.getProdId());
        });
        this.cartGateway.edit(cart);
    }
    public void edit(String id) {
        this.cartGateway.remove(id);
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
}
