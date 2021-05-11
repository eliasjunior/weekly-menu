package main.java.com.weeklyMenu.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Category {
    public Category() {
    }
    public Category(String id) {
        this.id = id;
    }

    private String id;
    private String name;
    private List<Product> products;
    private List<String> prodIds;
}
