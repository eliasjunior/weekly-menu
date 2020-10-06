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
        LOGGER.info("getAllRecipes");
        List<Recipe> recipes = recipeRepository.findAll();
        return MAPPER.recipesToRecipesDto(recipes);
    }

    @Override
    public RecipeDto save(RecipeDto recipeDto) {
        LOGGER.info("save", recipeDto);
        Recipe recipe = this.saveAndValidate(recipeDto, false);
        return MAPPER.recipeToRecipeDto(recipe);
    }

    @Override
    public void update(RecipeDto dto) {
        this.saveAndValidate(dto, true);
    }

    private Recipe saveAndValidate(RecipeDto recipeDto, boolean isUpdate) {
        Recipe oldRecipe = new Recipe();
        if (isUpdate) {
            Optional<Recipe> optional = recipeRepository.findById(recipeDto.getId());
            if (!optional.isPresent()) {
                throw new CustomValidationException("Recipe not found to update");
            }
            oldRecipe = optional.get();
        }

        this.recipeValidator.validateRecipeDto(recipeDto, this.validateProductsFromItems(recipeDto.getProdsDetail(), isUpdate));

        if (isNull(recipeDto.getId()) && !isUpdate) {
            recipeDto.setId(idGenerator.generateId());
        }

        recipeDto.generateItemsIds(idGenerator);

        if (isUpdate) {
            //remove all children to be add again afterwards
            oldRecipe.removeAll();
        }

        Recipe newRecipe = MAPPER.recipeDtoToRecipe(recipeDto);
        newRecipe.linkAllToRecipe();

        return recipeRepository.save(newRecipe);
    }

    @Override
    public void delete(String id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public RecipeDto getRecipe(String id) {
        return MAPPER.recipeToRecipeDto(recipeRepository.findById(id).get());
    }

    @Override
    public boolean isRecipeNameUsed(RecipeDto dto) {
        Recipe recipe = recipeRepository.findByName(dto.getName());
        return recipe != null;
    }

    // Cant add this to the validator because it rely on the repository
    private List<Product> validateProductsFromItems(List<ProdDetailDto> recipeItems, boolean isUpdate) {
        if (Objects.isNull(recipeItems) || recipeItems.size() == 0) {
            throw new CustomValidationException("Recipe must has at least one product");
        }

//        for (ProdDetailDto detailDto : recipeItems) {
//            checkQuantity(detailDto.getQuantity());
//            if (isUpdate && Objects.nonNull(detailDto.getId())) {
//                Optional<ProdDetail> op = prodDetailRepository.findById(detailDto.getId());
//                if (!op.isPresent()) {
        //TODO this was NOT correct because I can check new recipe items to the list and just add new Item to the list, CREATE a test to it, does it UNIT fits here ???
//                    throw new CustomValidationException("Prod detail id not found.");
//                }
//            }
//        }

        List<Product> products = recipeItems
                .stream()
                .map(prodDetailDto -> checkProduct(prodDetailDto.getProdId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        return products;
    }

    private Optional<Product> checkProduct(String productId) {
        if (Objects.isNull(productId)) {
            throw new CustomValidationException("Product id from ProdDetail cannot be null");
        }
        return productRepository.findById(productId);
    }

    private void checkQuantity(Integer quantity) {
        if (Objects.isNull(quantity)) {
            throw new CustomValidationException("Product quantity from ProdDetail cannot be null");
        }
    }
}
