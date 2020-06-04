package com.weeklyMenu.mapper;

import lombok.Data;

@Data
public class ProductPayload {
    private String id;
    private String catId;
    private String name;
    private String quantityType;
}
