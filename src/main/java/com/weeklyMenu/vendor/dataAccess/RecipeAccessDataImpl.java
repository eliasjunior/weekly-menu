package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.domain.data.RecipeDataAccess;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.repository.ProdDetailRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
public class RecipeAccessDataImpl implements RecipeDataAccess {
    final Logger LOGGER = LoggerFactory.getLogger(RecipeDataAccess.class);
    private final RecipeRepository recipeRepository;
    private final ProdDetailRepository prodDetailRepository;
    private final ProductRepository productRepository;
    private IdGenerator idGenerator;
    private final RecipeMapper MAPPER = RecipeMapper.INSTANCE;
    private DataAccessValidator recipeValidator;

    public RecipeAccessDataImpl(RecipeRepository recipeRepository,
                                ProdDetailRepository prodDetailRepository,
                                ProductRepository productRepository,
                                IdGenerator idGenerator,
                                DataAccessValidator recipeValidator) {
        this.recipeRepository = recipeRepository;
        this.prodDetailRepository = prodDetailRepository;
        this.productRepository = productRepository;
        this.idGenerator = idGenerator;
        this.recipeValidator = recipeValidator;
    }

    @Override
    public List<RecipeDto> getAllRecipes() {
        LOGGER.debug("getAllRecipes");
        List<Recipe> recipes = recipeRepository.findAll();
        return MAPPER.recipesToRecipesDto(recipes);
    }

    @Override
    public RecipeDto save(RecipeDto recipeDto) {
        LOGGER.debug("save" + recipeDto.toString());
        validateInDB(recipeDto);
        this.recipeValidator.validateRecipeDto(recipeDto,
                this.validateProductsFromItems(recipeDto.getProdsDetail()));

        if (isNull(recipeDto.getId()) ) {
            recipeDto.setId(idGenerator.generateId());
        }

        recipeDto.generateItemsIds(idGenerator);

        Recipe newRecipe = MAPPER.recipeDtoToRecipe(recipeDto);

        newRecipe.linkAllToRecipe(null);
        return MAPPER.recipeToRecipeDto( recipeRepository.save(newRecipe));
    }

    @Override
    public void update(RecipeDto recipeDto) {
        LOGGER.debug("update" + recipeDto.toString());
        validateInDB(recipeDto);
        Recipe oldRecipe;
        Optional<Recipe> optional = recipeRepository.findById(recipeDto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Recipe not found to update");
        }
        oldRecipe = optional.get();

        this.recipeValidator.validateRecipeDto(recipeDto,
                this.validateProductsFromItems(recipeDto.getProdsDetail()));

        recipeDto.generateItemsIds(idGenerator);
        //remove all children to be add again afterwards

        oldRecipe.removeAllItems(true);

        Recipe newRecipe = MAPPER.recipeDtoToRecipe(recipeDto);

        Optional<Recipe> optRec = recipeRepository.findById(newRecipe.getId());
        if (!optRec.isPresent()) {
            throw new CustomValidationException("Update failed because the recipe id sent by the request was not found!");
        }
        Recipe inBdRecipe = optRec.get();
        newRecipe.linkAllToRecipe(inBdRecipe.getBasicEntity());

        recipeRepository.save(newRecipe);
    }

    @Override
    public void delete(String id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public RecipeDto getRecipe(String id) {
        Optional<Recipe> optional = recipeRepository.findById(id);
        if(optional.isPresent()) {
            return MAPPER.recipeToRecipeDto(optional.get());
        }
        throw new CustomValidationException("Attempt to load recipe has failed");
    }

    @Override
    public boolean isRecipeNameUsed(RecipeDto dto) {
        Recipe recipe = recipeRepository.findByName(dto.getName());
        return recipe != null;
    }

    // Cant add this to the validator because it rely on the repository
    private List<Product> validateProductsFromItems(List<ProdDetailDto> recipeItems) {
        if (Objects.isNull(recipeItems) || recipeItems.size() == 0) {
            throw new CustomValidationException("Recipe must has at least one product");
        }
        // I cannot check id ProdDetail exists for recipe update because for the update case I can have
        // some existing items and just add new one, this new one the prodDetailId will be not in the DB, there are other
        // ways to check that, but will demand other changes or needs more investigation.
        return recipeItems
                .stream()
                .map(prodDetailDto -> checkProduct(prodDetailDto.getProdId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    private Optional<Product> checkProduct(String productId) {
        if (Objects.isNull(productId)) {
            throw new CustomValidationException("Product id from ProdDetail cannot be null");
        }
        return productRepository.findById(productId);
    }

    private void validateInDB(RecipeDto dto) {
        if (Objects.isNull(dto.getId())) {
            Recipe recInDb = recipeRepository.findByNameIgnoreCase(dto.getName());
            if (Objects.nonNull(recInDb)) {
                throw new CustomValidationException("Attempt to save new recipe failed, recipe with this name already exists.");
            }
        } else {
            Recipe recExisting = recipeRepository.findByNameIgnoreCaseAndIdIsDiff(dto.getName(), dto.getId());
            if (Objects.nonNull(recExisting)) {
                throw new CustomValidationException("Attempt to save a new recipe has failed because there is a recipe with the same name.");
            }
        }
    }
}
