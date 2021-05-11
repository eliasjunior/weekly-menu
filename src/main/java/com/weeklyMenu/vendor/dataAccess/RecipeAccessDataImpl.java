package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.RecipeDB;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import main.java.com.weeklyMenu.entity.Recipe;
import main.java.com.weeklyMenu.gateway.RecipeGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeAccessDataImpl implements RecipeGateway {
    final Logger LOGGER = LoggerFactory.getLogger(RecipeAccessDataImpl.class);
    private final RecipeRepository recipeRepository;
    private final RecipeMapper MAPPER = RecipeMapper.INSTANCE;

    public RecipeAccessDataImpl(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Optional<Recipe> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        LOGGER.debug("getAllRecipes");
        List<RecipeDB> recipes = recipeRepository.findAll();
        return MAPPER.recipesDBToRecipes(recipes);
    }

    @Override
    public void remove(String id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe findByNameIgnoreCase(String name) {
        return MAPPER.recipeDBToRecipe(recipeRepository.findByNameIgnoreCase(name));
    }

    @Override
    public Recipe findByNameIgnoreCaseAndIdIsDiff(String name, String id) {
        return MAPPER.recipeDBToRecipe(recipeRepository.findByNameIgnoreCaseAndIdIsDiff(name, id));
    }

    @Override
    public Recipe findByName(String name) {
        return MAPPER.recipeDBToRecipe(recipeRepository.findByName(name));
    }

    @Override
    public Recipe create(Recipe recipe) {
        LOGGER.debug("save" + recipe.toString());
        RecipeDB newRecipe = MAPPER.recipeToRecipeDB(recipe);
        newRecipe.linkAllToRecipe(null);
        return MAPPER.recipeDBToRecipe( recipeRepository.save(newRecipe));
    }

    @Override
    public void edit(Recipe recipe) {
        LOGGER.debug("update" + recipe.toString());
        RecipeDB newRecipe = MAPPER.recipeToRecipeDB(recipe);

        //remove all children to be add again afterwards
        RecipeDB oldRecipe = recipeRepository.findByName(recipe.getName());
        //TODO review this models logic, move to use cases
        oldRecipe.removeAllItems(true);
        newRecipe.linkAllToRecipe(oldRecipe.getBasicEntity());

        recipeRepository.save(newRecipe);
    }
}
