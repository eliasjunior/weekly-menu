package com.weeklyMenu.vendor.model;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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

    @Column(name = "CAT_ID")
    private String catId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "QUANTITY_TYPE")
    private String quantityType;
}