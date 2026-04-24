package com.weeklyMenu.adaptor.model;

import com.weeklyMenu.useCase.common.GlobalConstant;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

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
            this.setStatus(GlobalConstant.STATUS_ACTIVE);
        } else {
            this.setStatus(inDb.getStatus());
            this.setDateCreated(inDb.getDateCreated());
            this.setDateUpdate(new Date().toString());
        }
    }
}
