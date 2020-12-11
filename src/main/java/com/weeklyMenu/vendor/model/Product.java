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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static com.weeklyMenu.helpers.GlobalConstant.STATUS_ACTIVE;

/**
 * Product
 */
@Table(name = "PRODUCT")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
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
    private Category category;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProdDetail> prodsDetail;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();

    public void updateBasic(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);
    }
}