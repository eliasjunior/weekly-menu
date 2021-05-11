package main.java.com.weeklyMenu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.java.com.weeklyMenu.gateway.ProductGateway;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private String id;
    private String name;
    private String prodId;
    private Set<String> recipes;
    boolean selected;
    private Product product;
}
