package com.weeklyMenu.vendor.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String id;

    @Column(name = "NAME", unique = true)
    @NotNull
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<ProdDetail> prodsDetail;

    @ManyToMany(mappedBy = "selectedRecipes")
    private Set<CartItem> cartItems;

    public void removeAllItems(boolean isUpdate) {
        if (isUpdate) {
            for (ProdDetail prodDetail : this.getProdsDetail()) {
                prodDetail.setRecipe(null);
            }
            this.getProdsDetail().clear();
        }
    }

    public void linkAllToRecipe(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);

        for (ProdDetail prodDetail : this.getProdsDetail()) {
            prodDetail.setRecipe(this);
            prodDetail.getBasicEntity().updateBasic(prodDetail.getBasicEntity());
        }
    }

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();
}
