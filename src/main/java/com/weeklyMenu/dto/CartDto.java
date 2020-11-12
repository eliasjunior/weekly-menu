package com.weeklyMenu.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@Validated
public class CartDto {
    private String id;
    private String name;
    private List<CartItemDto> cartItems;
}
