package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "RECIPE")
@Entity
@Data
public class Recipe {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProdDetail> prodsDetail;

    // lombok stack overflow that would call product toString(products)
    public String toString() {
        return "Recipe(id=" + this.getId() + ", name=" + this.getName() + ", prodsDetail="+ prodsDetail.size();
    }
}
