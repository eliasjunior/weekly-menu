package com.weeklyMenu.vendor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
public class CartItem {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @Column(name = "NAME")
    private String name;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROD_ID", referencedColumnName = "ID", unique = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Column(name = "SELECTED")
    private boolean selected;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "CART_ITEM_RECIPES",
            joinColumns = @JoinColumn(name = "CART_ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "RECIPE_ID"))
    private Set<Recipe> selectedRecipes;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

    public void updateBasic(CartItem itemDB) {
        this.getBasicEntity().updateBasic(itemDB.getBasicEntity());
    }

    public CartItem findCartItem(String id, List<CartItem> itemsDB) {
        return itemsDB
                .stream()
                .filter(cartItem -> cartItem.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
