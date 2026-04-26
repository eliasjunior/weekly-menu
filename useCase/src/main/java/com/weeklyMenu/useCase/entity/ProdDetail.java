package com.weeklyMenu.useCase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdDetail {
    private String id;
    private String prodId;
    private Integer quantity;
}
