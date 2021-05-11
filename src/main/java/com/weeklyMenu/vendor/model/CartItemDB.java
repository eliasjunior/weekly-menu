package com.weeklyMenu.vendor.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CART_ITEM")
@EqualsAndHashCode(exclude = "selectedRecipes")
public class CartItemDB {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @Column(name = "NAME")
    private String name;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROD_ID", referencedColumnName = "ID", unique = true)
    private ProductDB product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID")
    private CartDB cart;

    @Column(name = "SELECTED")
    private boolean selected;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "CART_ITEM_RECIPES",
            joinColumns = @JoinColumn(name = "CART_ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "RECIPE_ID"))
    private Set<RecipeDB> selectedRecipes;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

    public void updateBasic(CartItemDB itemDB) {
        this.getBasicEntity().updateBasic(itemDB.getBasicEntity());
    }

    public CartItemDB findCartItem(String id, List<CartItemDB> itemsDB) {
        return itemsDB
                .stream()
                .filter(cartItem -> cartItem.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
