package com.weeklyMenu.adaptor.gateway;

import com.weeklyMenu.adaptor.springData.RecipeRepository;
import com.weeklyMenu.adaptor.mapper.RecipeMapper;
import com.weeklyMenu.adaptor.model.RecipeDB;
import com.weeklyMenu.useCase.entity.Recipe;
import com.weeklyMenu.useCase.gateway.RecipeGateway;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

public class RecipeGatewayImpl implements RecipeGateway {
    private final Logger logger;
    private final RecipeRepository recipeRepository;
    private final RecipeMapper mapper;

    public RecipeGatewayImpl(Logger logger, RecipeRepository recipeRepository, RecipeMapper mapper){
        this.logger = logger;
        this.recipeRepository = recipeRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Recipe> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        logger.debug("getAllRecipes");
        List<RecipeDB> recipes = recipeRepository.findAll();
        return mapper.recipesDBToRecipes(recipes);
    }

    @Override
    public void remove(String id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe findByNameIgnoreCase(String name) {
        return mapper.recipeDBToRecipe(recipeRepository.findByNameIgnoreCase(name));
    }

    @Override
    public Recipe findByNameIgnoreCaseAndIdIsDiff(String name, String id) {
        return mapper.recipeDBToRecipe(recipeRepository.findByNameIgnoreCaseAndIdIsDiff(name, id));
    }

    @Override
    public Recipe findByName(String name) {
        return mapper.recipeDBToRecipe(recipeRepository.findByName(name));
    }

    @Override
    public Recipe create(Recipe recipe) {
        logger.debug("save" + recipe.toString());
        RecipeDB newRecipe = mapper.recipeToRecipeDB(recipe);
        newRecipe.linkAllToRecipe(null);
        return mapper.recipeDBToRecipe( recipeRepository.save(newRecipe));
    }

    @Override
    public void edit(Recipe recipe) {
        logger.debug("update" + recipe.toString());
        RecipeDB newRecipe = mapper.recipeToRecipeDB(recipe);

        //remove all children to be add again afterwards
        RecipeDB oldRecipe = recipeRepository.findByName(recipe.getName());
        //TODO review this models logic, move to use cases
        oldRecipe.removeAllItems(true);
        newRecipe.linkAllToRecipe(oldRecipe.getBasicEntity());

        recipeRepository.save(newRecipe);
    }
}
