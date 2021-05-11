package main.java.com.weeklyMenu.mapper;

import lombok.Data;
import java.util.List;

@Data
public class ProductItemMapper {
    public String id;
    public String prodId;
    public List<RecipeMapper> recipes;
}
