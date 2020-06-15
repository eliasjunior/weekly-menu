package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "PRODUCT_ITEM")
public class ProductItem extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROD_ID", referencedColumnName = "ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Column(name ="SELECTED")
    private boolean selected;

    @OneToMany(mappedBy = "productItem", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Recipe> recipes;
}
