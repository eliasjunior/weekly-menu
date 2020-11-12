package com.weeklyMenu.vendor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Table(name = "RECIPE")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<ProdDetail> prodsDetail;

    @ManyToMany(mappedBy = "selectedRecipes")
    private Set<CartItem> cartItems;

    public void removeAllItems() {
        Iterator<ProdDetail> iterator = this.getProdsDetail().iterator();
        while (iterator.hasNext()) {
            ProdDetail prodDetail = iterator.next();
            prodDetail.setRecipe(null);
        }
        this.getProdsDetail().clear();
    }

    public void linkAllToRecipe() {
        for (int i = 0; i <  this.getProdsDetail().size(); i++) {
            ProdDetail prodDetail = this.getProdsDetail().get(i);
            prodDetail.setRecipe(this);
        }
    }
}
