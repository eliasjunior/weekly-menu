package com.weeklyMenu.vendor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "CART")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cart")
    private List<CartItem> cartItems;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

}
