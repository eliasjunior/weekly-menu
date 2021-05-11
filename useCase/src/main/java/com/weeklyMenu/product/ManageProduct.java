package main.java.com.weeklyMenu.product;

import main.java.com.weeklyMenu.entity.Product;

public interface ManageProduct {
    Product create(Product product);

    void edit(Product dto);

    void remove(String id);
}
