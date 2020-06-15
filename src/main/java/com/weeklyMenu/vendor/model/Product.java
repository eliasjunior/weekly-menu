package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Product
 */
@Table(name = "PRODUCT")
@Entity
@Data
public class Product {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "QUANTITY_TYPE")
    private String quantityType;

    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    private Category category;

}