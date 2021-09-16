package com.weeklyMenu.adaptor.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "PROD_DETAIL")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdDetailDB {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "REC_ID")
    private RecipeDB recipe;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "PROD_ID")
    private ProductDB product;

    @Embedded
    private BasicEntity basicEntity = new BasicEntity();
}
