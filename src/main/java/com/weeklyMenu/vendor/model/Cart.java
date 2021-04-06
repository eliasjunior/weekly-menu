package com.weeklyMenu.vendor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "CART")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "cart")
    private List<CartItem> cartItems;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

    public void linkItemsToCart(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);
        if( basicEntity == null) {
            for (CartItem cartItem : getCartItems()) {
                cartItem.setCart(this);
                cartItem.getBasicEntity().updateBasic(null);
            }
        }
    }
    public void linkItemsToCart(BasicEntity basicEntity, List<CartItem> itemsDB) {
        this.getBasicEntity().updateBasic(basicEntity);
        for (CartItem cartItem : this.getCartItems()) {
            cartItem.setCart(this);
            CartItem itemFound = cartItem.findCartItem(cartItem.getId(), itemsDB);
            if(itemFound == null) {
                cartItem.getBasicEntity().updateBasic(null);
            } else {
                cartItem.updateBasic(itemFound);
            }
        }
    }

    public void removeAllItems() {
        for (CartItem cartItem: this.getCartItems()) {
            cartItem.setCart(null);
        }
        this.getCartItems().clear();
    }
}
