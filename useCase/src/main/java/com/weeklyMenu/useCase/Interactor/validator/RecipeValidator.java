package com.weeklyMenu.useCase.Interactor.validator;

import com.weeklyMenu.useCase.entity.ProdDetail;
import com.weeklyMenu.useCase.entity.Product;
import com.weeklyMenu.useCase.entity.Recipe;
import com.weeklyMenu.useCase.exceptions.CustomValidationException;
import com.weeklyMenu.useCase.gateway.RecipeGateway;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class RecipeValidator {
    final Logger LOGGER;
    private final RecipeGateway recipeGateway;
    private final ProductValidator productValidator;
    private Map<String, Recipe> recipeLookup;

    public RecipeValidator(RecipeGateway recipeGateway, ProductValidator productValidator, Logger logger) {
        this.recipeGateway = recipeGateway;
        this.productValidator = productValidator;
        this.LOGGER = logger;
    }

    public void validateRecipes(Set<String> recipes) {
        if (Objects.nonNull(recipes)) {
            if (Objects.isNull(recipeLookup)) {
                recipeLookup = new HashMap<>();
            }
            for (String id : recipes) {
                Recipe recIn = recipeLookup.get(id);
                if (Objects.isNull(recIn)) {
                    Optional<Recipe> optional = recipeGateway.findById(id);
                    if (!optional.isPresent()) {
                        String msgError = "Attempt to save cartItem but recipe does not exist, id=" + id;
                        throw new CustomValidationException(msgError);
                    }
                }
            }
        }
    }

    public void validateRecipeWithProducts(Recipe recipe) {
        try {
            List<Product> products = validateProductsFromItems(recipe.getProdsDetail());
            Objects.requireNonNull(recipe.getProdsDetail(),
                    "Input for recipe creation is not valid, there are no products");

            List<ProdDetail> recipeItems = recipe.getProdsDetail();

            if (recipeItems.size() != products.size()) {
                String msgError = "Attempt to save Prod Detail of recipe " + recipe.getName() + "\n";

                List<String> prodIds = products
                        .stream()
                        .map(product -> product.getId())
                        .collect(toList());

                List<ProdDetail> missingProds = recipeItems
                        .stream()
                        .filter(item -> !prodIds.contains(item.getProdId()))
                        .collect(toList());

                String missingProdsMessage = missingProds.stream()
                        .map(prodDetail -> "Product id not found in the db  " +  prodDetail.getProdId())
                        .collect(Collectors.joining());

                throw new CustomValidationException(msgError.concat(missingProdsMessage));
            }
        } catch (RuntimeException e) {
            LOGGER.error("validateRecipe ", e);
            throw new CustomValidationException(e.getMessage());
        }
    }

    public void validateRecipe(Recipe recipe) {
        if (Objects.isNull(recipe.getId())) {
            Recipe recInDb = recipeGateway.findByNameIgnoreCase(recipe.getName());
            if (Objects.nonNull(recInDb)) {
                throw new CustomValidationException("Attempt to save new recipe failed, recipe with this name already exists.");
            }
        } else {
            Recipe recExisting = recipeGateway.findByNameIgnoreCaseAndIdIsDiff(recipe.getName(), recipe.getId());
            if (Objects.nonNull(recExisting)) {
                throw new CustomValidationException("Attempt to save a new recipe has failed because there is a recipe with the same name.");
            }
        }
    }

    // Cant add this to the validator because it rely on the repository
    private List<Product> validateProductsFromItems(List<ProdDetail> recipeItems) {
        if (Objects.isNull(recipeItems) || recipeItems.size() == 0) {
            throw new CustomValidationException("Recipe must has at least one product");
        }
        // I cannot check id ProdDetail exists for recipe update because for the update case I can have
        // some existing items and just add new one, this new one the prodDetailId will be not in the DB, there are other
        // ways to check that, but will demand other changes or needs more investigation.
        return recipeItems
                .stream()
                .map(prodDetail -> productValidator.validateProduct(prodDetail.getProdId()))
                .collect(toList());
    }

}
