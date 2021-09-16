package com.weeklyMenu.useCase.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProdDetail {
    private String id;
    private String prodId;
    private Integer quantity;
}
