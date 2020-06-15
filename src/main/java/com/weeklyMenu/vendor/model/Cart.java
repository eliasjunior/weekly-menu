package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "CART")
public class Cart extends BaseEntity {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cart")
    private List<ProductItem> productItems;

    @Override
    public String toString() {
        return "ShoppingList(" + super.toString() + ", items="+ productItems.size() +")";
    }
}
