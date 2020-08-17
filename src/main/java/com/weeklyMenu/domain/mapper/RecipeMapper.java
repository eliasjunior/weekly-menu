package com.weeklyMenu.domain.mapper;

import lombok.Data;

import java.util.List;

@Data
public class RecipeMapper {
    private String id;
    private String name;
    private List<ProdDetailMapper> prodsDetail;
    private ProductItemMapper cartItem;

    // lombok stack overflow that would call product toString(products)
    public String toString() {
        return "Recipe(id=" + this.getId() + ", name=" + this.getName() + ", prodsDetail=" + (prodsDetail != null ? prodsDetail.size() : "") + ")";
    }
}
