package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @Column(name = "ID")
    private String id;

//    @Version
//    private Integer version;

    @Column(name = "NAME")
    private String name;
}
