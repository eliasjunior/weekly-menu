package com.weeklyMenu.adaptor.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "CART")
@NoArgsConstructor
@AllArgsConstructor
public class CartDB {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "cart")
    private List<CartItemDB> cartItems;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

    public void linkItemsToCart(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);
        if( basicEntity == null) {
            for (CartItemDB cartItemDB : getCartItems()) {
                cartItemDB.setCart(this);
                cartItemDB.getBasicEntity().updateBasic(null);
            }
        }
    }
    public void linkItemsToCart(BasicEntity basicEntity, List<CartItemDB> itemsDB) {
        this.getBasicEntity().updateBasic(basicEntity);
        for (CartItemDB cartItemDB : this.getCartItems()) {
            cartItemDB.setCart(this);
            CartItemDB itemFound = cartItemDB.findCartItem(cartItemDB.getId(), itemsDB);
            if(itemFound == null) {
                cartItemDB.getBasicEntity().updateBasic(null);
            } else {
                cartItemDB.updateBasic(itemFound);
            }
        }
    }

    public void removeAllItems() {
        for (CartItemDB cartItemDB : this.getCartItems()) {
            cartItemDB.setCart(null);
        }
        this.getCartItems().clear();
    }
}
