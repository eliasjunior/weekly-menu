package com.weeklyMenu.domain.useCases;

import com.weeklyMenu.domain.data.ProductItemDataAccess;
import com.weeklyMenu.domain.mapper.ProductItemMapper;

import java.util.List;

public class ProductItemDomain {
    private ProductItemDataAccess productItemDataAccess;

    public ProductItemDomain(ProductItemDataAccess productItemDataAccess) {
        this.productItemDataAccess = productItemDataAccess;
    }


    public List<ProductItemMapper> fillWithItems(List<ProductItemMapper> listItems) {

        //TODO see case bellow, there is not business logic, its only checking if constraints are missing
        // for pure use case business logic a better example would something else
        return null;
//            listItems.forEach(productItemMapper -> {
//                productItemMapper.getRecipes().forEach(recipeMapper -> {
//                    if(recipeMapper )
//                    Optional<Recipe> optional = recipeRepository.findById(recipe.getId());
//                    if (optional.isPresent()) {
//                        Recipe recIn = optional.get();
//                        recIn.setProductItem(cartItem);
//                        return recIn;
//                    } else {
//                        String msgError = "Attempt to save cartItem but recipe does not exist, id=" + recipe.getId();
//                        throw new CustomValidationException(msgError);
//                    }
//                });
//            });
    }
}
