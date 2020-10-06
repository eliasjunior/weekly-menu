package com.weeklyMenu.vendor.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Iterator;
import java.util.List;

@Table(name = "RECIPE")
@Entity
@Data
public class Recipe extends BaseEntity {
    @ToString.Exclude
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdDetail> prodsDetail;

    @ManyToOne
    @JoinColumn(name = "PROD_ITEM_ID")
    private CartItem cartItem;

    public void removeAll() {
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
