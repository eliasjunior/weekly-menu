package com.weeklyMenu.adaptor.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Product
 */
@Table(name = "PRODUCT")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDB {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @Column(name = "NAME", unique=true)
    @NotNull
    private String name;

    @Column(name = "QUANTITY_TYPE")
    @NotNull
    private String quantityType;

    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    private CategoryDB category;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProdDetailDB> prodsDetail;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

    public void updateBasic(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);
    }
}