package com.weeklyMenu.webAdaptor.data;

import com.weeklyMenu.webAdaptor.mapper.CartItemMapper;
import com.weeklyMenu.webAdaptor.mapper.CartMapper;
import com.weeklyMenu.webAdaptor.model.CartDB;
import com.weeklyMenu.webAdaptor.repository.CartRepository;
import main.java.com.weeklyMenu.entity.Cart;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.CartGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartGatewayImpl implements CartGateway {
    final Logger LOGGER = LoggerFactory.getLogger(CartGatewayImpl.class);
    private final CartRepository cartRepository;
    private final CartMapper MAPPER = CartMapper.INSTANCE;

    CartItemMapper cartItemMapper = new CartItemMapper();

    public CartGatewayImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart create(Cart cart) {
        LOGGER.debug("--- save --- " + cart.toString());

        CartDB dbMapper = MAPPER.cartToCartDB(cart);

        //TODO review here, move useCases
        dbMapper.setCartItems(cartItemMapper.cartItemsToCartItems(cart.getCartItems()));
        dbMapper.linkItemsToCart(null);

        CartDB cartNew = cartRepository.save(dbMapper);
        Cart cartEntity = MAPPER.cartDBToCart(cartNew);
        cartNew.setCartItems(cartItemMapper.cartItemsToCartItems(cartEntity.getCartItems()));

        return cartEntity;
    }

    @Override
    public void edit(Cart cart) {
        LOGGER.debug("--- update --- " + cart.toString());

        CartDB dbMapper = MAPPER.cartToCartDB(cart);

        //TODO review these links
        dbMapper.linkItemsToCart(dbMapper.getBasicEntity(), dbMapper.getCartItems());
        dbMapper.removeAllItems();

        cartRepository.save(dbMapper);
    }

    @Override
    public void remove(String id) {
        cartRepository.deleteById(id);
    }

    @Override
    public List<Cart> getCartList() {
        List<CartDB> cartsDB = cartRepository.findAllActives();
        List<Cart> carts = MAPPER.cartsDBToCarts(cartsDB);
        for (int i = 0; i < carts.size(); i++) {
            CartDB dbMapper = cartsDB.get(i);
            Cart cart = carts.get(i);
            dbMapper.setCartItems(cartItemMapper.cartItemsToCartItems(cart.getCartItems()));
        }
        return carts;
    }

    @Override
    public Optional<Cart> findById(String id) {
        Optional<CartDB> optional = cartRepository.findById(id);
        if (optional.isEmpty()) {
            throw new CustomValidationException("Attempt to retrieve cart has failed!");
        }
        return Optional.of(MAPPER.cartDBToCart(optional.get()));
    }
}
