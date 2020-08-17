package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DataAccessValidator {
    final Logger LOGGER = LoggerFactory.getLogger(DataAccessValidator.class);

    protected void validateRecipeDto(RecipeDto recipeDto, List<Product> products) {
        try {
            Objects.requireNonNull(recipeDto.getProdsDetail(),
                    "Input for recipe creation is not valid, there are no products");

            List<ProdDetailDto> recipeItems = recipeDto.getProdsDetail();

            if (recipeItems.size() != products.size()) {
                String msgError = "Attempt to save Prod Detail of recipe " + recipeDto.getName() + "\n";

                List<String> prodIds = products
                        .stream()
                        .map(product -> product.getId())
                        .collect(toList());

                List<ProdDetailDto> missingProds = recipeItems
                        .stream()
                        .filter(item -> !prodIds.contains(item.getProdId()))
                        .collect(toList());

                String missingProdsMessage = missingProds.stream()
                        .map(prodDetailDto -> "Product id not found in the db  " +  prodDetailDto.getProdId())
                        .collect(Collectors.joining());

                throw new CustomValidationException(msgError.concat(missingProdsMessage));
            }
        } catch (RuntimeException e) {
            LOGGER.error("validateRecipeDto ", e);
            throw new CustomValidationException(e.getMessage());
        }
    }

    protected void validateProductDto(ProductDto productDto) {
        try {
            Objects.requireNonNull(productDto.getCatId(),
                    "Input for product creation is not valid, there is no category id");
        } catch (RuntimeException e) {

            throw new CustomValidationException(e.getMessage());
        }
    }

    protected void validateCartDto(CartDto dto) {
        try {
            Objects.requireNonNull(dto.getProductItems(), "List<ProductItems> from CartDto cannot be null");
            dto.getProductItems().forEach(item -> {
                Objects.requireNonNull(item.getProdId(),
                        "Product (item) ID from cart is missing");
                if (Objects.nonNull(item.getRecipes())) {
                    item.getRecipes().forEach(recipeDto -> {
                        Objects.requireNonNull(recipeDto.getId(),
                                "Recipe (recipes[{id}]) ID from cart is missing");
                    });
                }
            });

        } catch (RuntimeException e) {
            LOGGER.error("validateCartDto ", e);
            throw new CustomValidationException(e.getMessage());
        }
    }
}
