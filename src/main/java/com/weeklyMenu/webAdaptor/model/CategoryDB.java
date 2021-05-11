package com.weeklyMenu.webAdaptor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "CATEGORY")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDB {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @NotNull
    @Column(name = "NAME", unique=true)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductDB> products;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

    public void updateBasic(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);
    }
}
