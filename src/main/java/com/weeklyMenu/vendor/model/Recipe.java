package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "RECIPE")
@Entity
@Data
public class Recipe extends BaseEntity {
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProdDetail> prodsDetail;

    @ManyToOne
    @JoinColumn(name = "PROD_ITEM_ID")
    private ProductItem productItem;

    // lombok stack overflow that would call product toString(products)
    public String toString() {
        return "Recipe(id=" + this.getId() + ", name=" + this.getName() + ", prodsDetail=" + (prodsDetail != null ? prodsDetail.size() : "") + ")";
    }
}
