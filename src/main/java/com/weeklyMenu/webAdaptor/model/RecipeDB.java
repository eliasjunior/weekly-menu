package com.weeklyMenu.webAdaptor.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Table(name = "RECIPE")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDB {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @Column(name = "NAME", unique = true)
    @NotNull
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<ProdDetailDB> prodsDetail;

    @ManyToMany(mappedBy = "selectedRecipes")
    private Set<CartItemDB> cartItemDBS;

    public void removeAllItems(boolean isUpdate) {
        if (isUpdate) {
            for (ProdDetailDB prodDetailDB : this.getProdsDetail()) {
                prodDetailDB.setRecipe(null);
            }
            this.getProdsDetail().clear();
        }
    }

    public void linkAllToRecipe(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);

        for (ProdDetailDB prodDetailDB : this.getProdsDetail()) {
            prodDetailDB.setRecipe(this);
            prodDetailDB.getBasicEntity().updateBasic(prodDetailDB.getBasicEntity());
        }
    }

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();
}
