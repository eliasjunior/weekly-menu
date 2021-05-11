package com.weeklyMenu.vendor.controller;

import main.java.com.weeklyMenu.Interactor.recipe.FindRecipe;
import main.java.com.weeklyMenu.Interactor.recipe.ManageRecipe;
import main.java.com.weeklyMenu.common.GlobalConstant;
import main.java.com.weeklyMenu.entity.Recipe;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(GlobalConstant.BASE_URL + "/recipes")
public class RecipeController {
    final FindRecipe findRecipe;
    final ManageRecipe manageRecipe;
    final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    public RecipeController(FindRecipe findRecipe, ManageRecipe manageRecipe) {
        this.findRecipe = findRecipe;
        this.manageRecipe = manageRecipe;
    }

    @GetMapping
    public List<Recipe> getRecipes() {
        LOGGER.info("--> getRecipes");
        return findRecipe.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable String id) {
        LOGGER.info("--> getRecipe {}", id);
        return findRecipe.getRecipe(id);
    }

    @PostMapping
    public  ResponseEntity<Recipe> create(@RequestBody Recipe productDto) {
        LOGGER.info("--> save");
        boolean isCreated = findRecipe.isRecipeNameUsed(productDto);
        if (!isCreated) {
            Recipe recipeDto = manageRecipe.create(productDto);
            return ResponseEntity
                    .created(URI.create(String.format(GlobalConstant.BASE_URL + "/recipes/%s", recipeDto.getId())))
                    .body(recipeDto);
        } else {
            throw new CustomValidationException("Name already exits ", new RuntimeException());
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Recipe dto) {
        LOGGER.info("--> update");
        manageRecipe.edit(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        manageRecipe.remove(id);
        return ResponseEntity.noContent().build();
    }
}
