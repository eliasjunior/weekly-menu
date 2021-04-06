package com.weeklyMenu.vendor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "PROD_DETAIL")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdDetail {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "REC_ID")
    private Recipe recipe;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "PROD_ID")
    private Product product;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();
}
