package com.weeklyMenu.adaptor.gateway;

import com.weeklyMenu.adaptor.SpringData.CartRepository;
import com.weeklyMenu.useCase.entity.Cart;
import com.weeklyMenu.useCase.gateway.CartGateway;
import com.weeklyMenu.adaptor.mapper.CartItemMapper;
import com.weeklyMenu.adaptor.mapper.CartMapper;
import com.weeklyMenu.adaptor.model.CartDB;
import com.weeklyMenu.useCase.exceptions.CustomValidationException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class CartGatewayImpl implements CartGateway {
    private final Logger logger;
    private final CartRepository cartRepository;
    private final CartMapper mapper;
    private final CartItemMapper cartItemMapper;

    public CartGatewayImpl(Logger logger, CartRepository cartRepository, CartMapper cartMapper, CartItemMapper cartItemMapper) {
        this.logger = logger;
        this.cartRepository = cartRepository;
        this.mapper = cartMapper;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    public Cart create(Cart cart) {
        logger.debug("--- save --- " + cart.toString());
        CartDB dbMapper = mapper.cartToCartDB(cart);

        dbMapper.setCartItems(cartItemMapper.cartItemsToCartItems(cart.getCartItems()));
        dbMapper.linkItemsToCart(null);

        CartDB cartNew = cartRepository.save(dbMapper);
        Cart cartEntity = mapper.cartDBToCart(cartNew);
        cartNew.setCartItems(cartItemMapper.cartItemsToCartItems(cartEntity.getCartItems()));

        return cartEntity;
    }

    @Override
    public void edit(Cart cart) {
        logger.debug("--- update --- " + cart.toString());

        CartDB dbMapper = mapper.cartToCartDB(cart);

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
        List<Cart> carts = mapper.cartsDBToCarts(cartsDB);
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
        return Optional.of(mapper.cartDBToCart(optional.get()));
    }
}
