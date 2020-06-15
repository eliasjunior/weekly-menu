package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "CATEGORY")
@Entity
@Data
public class Category extends BaseEntity {
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Product> products;

    // lombok stack overflow that would call product toString(products)
    public String toString() {
        return "Category(id=" + this.getId() + ", name=" + this.getName();
    }
}
