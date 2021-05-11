package com.weeklyMenu.vendor.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static main.java.com.weeklyMenu.common.GlobalConstant.STATUS_ACTIVE;

@Embeddable
@Data
public class BasicEntity {
    @Column(name = "DATE_CREATED")
    private String dateCreated;

    @Column(name = "DATE_UPDATED")
    private String dateUpdate;

    @Column(name = "STATUS")
    @NotNull
    private String status;

    public void updateBasic(BasicEntity inDb) {
        if (inDb == null) {
            this.setDateCreated(new Date().toString());
            this.setStatus(STATUS_ACTIVE);
        } else {
            this.setStatus(inDb.getStatus());
            this.setDateCreated(inDb.getDateCreated());
            this.setDateUpdate(new Date().toString());
        }
    }
}
