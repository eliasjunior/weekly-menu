package com.weeklyMenu.vendor.controller;

import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.helpers.GlobalConstant;
import com.weeklyMenu.domain.data.RecipeDataAccess;
import com.weeklyMenu.dto.RecipeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(GlobalConstant.BASE_URL + "/recipes")
public class RecipeController {
    final RecipeDataAccess recipeDataAccess;
    final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    public RecipeController(RecipeDataAccess recipeDataAccess) {
        this.recipeDataAccess = recipeDataAccess;
    }

    @GetMapping
    public List<RecipeDto> getRecipes() {
        LOGGER.info("--> getRecipes");
        return recipeDataAccess.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDto getRecipe(@PathVariable String id) {
        LOGGER.info("--> getRecipe {}", id);
        return recipeDataAccess.getRecipe(id);
    }

    @PostMapping
    public  ResponseEntity<RecipeDto> create(@RequestBody RecipeDto productDto) {
        LOGGER.info("--> save");
        boolean isCreated = recipeDataAccess.isRecipeNameUsed(productDto);
        if (!isCreated) {
            RecipeDto recipeDto = recipeDataAccess.save(productDto);
            return ResponseEntity
                    .created(URI.create(String.format(GlobalConstant.BASE_URL + "/recipes/%s", recipeDto.getId())))
                    .body(recipeDto);
        } else {
            throw new CustomValidationException("Name already exits ", new RuntimeException());
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody RecipeDto dto) {
        LOGGER.info("--> update");
        recipeDataAccess.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        recipeDataAccess.delete(id);
        return ResponseEntity.noContent().build();
    }
}
