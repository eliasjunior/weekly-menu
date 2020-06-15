package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "PROD_DETAIL")
@Entity
@Data
public class ProdDetail {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "REC_ID")
    private Recipe recipe;
}