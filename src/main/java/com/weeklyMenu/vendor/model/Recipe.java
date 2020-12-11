package com.weeklyMenu.vendor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.weeklyMenu.helpers.GlobalConstant.STATUS_ACTIVE;

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

    @Column(name = "NAME", unique=true)
    @NotNull
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<ProdDetail> prodsDetail;

    @ManyToMany(mappedBy = "selectedRecipes")
    private Set<CartItem> cartItems;

    public void removeAllItems(boolean isUpdate) {
        if(isUpdate) {
            Iterator<ProdDetail> iterator = this.getProdsDetail().iterator();
            while (iterator.hasNext()) {
                ProdDetail prodDetail = iterator.next();
                prodDetail.setRecipe(null);
            }
            this.getProdsDetail().clear();
        }
    }

    public void linkAllToRecipe(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);

        for (ProdDetail prodDetail: this.getProdsDetail()) {
            prodDetail.setRecipe(this);
            prodDetail.getBasicEntity().updateBasic(prodDetail.getBasicEntity());
        }
    }
    @Embedded
    private BasicEntity basicEntity = new BasicEntity();
}
