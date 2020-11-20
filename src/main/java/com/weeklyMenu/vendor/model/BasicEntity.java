package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Data
public class BasicEntity {
    @Column(name = "DATE_CREATED")
    private final String dateCreated = new Date().toString();

    @Column(name = "DATE_UPDATED")
    private String dateUpdate;

    @Column(name = "STATUS")
    private String status = "a";
}
