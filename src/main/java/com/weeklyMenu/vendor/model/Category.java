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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static com.weeklyMenu.helpers.GlobalConstant.STATUS_ACTIVE;

@Table(name = "CATEGORY")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @NotNull
    @Column(name = "NAME", unique=true)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Product> products;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

    public void updateBasic(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);
    }
}
