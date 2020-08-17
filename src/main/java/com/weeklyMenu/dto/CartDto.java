package com.weeklyMenu.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Data
@Validated
public class CartDto {
    private String id;
    private String name;
    //TODO should be cartItems
    private List<CartItemDto> productItems;
}
