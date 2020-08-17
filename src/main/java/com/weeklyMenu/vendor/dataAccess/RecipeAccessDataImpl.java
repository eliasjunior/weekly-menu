package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.domain.data.RecipeDataAccess;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.model.ProdDetail;
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
        Optional<Recipe> optional = recipeRepository.findById(dto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Recipe not found to update", new RuntimeException());
        }
        this.saveAndValidate(dto, true);
    }

    private Recipe saveAndValidate(RecipeDto recipeDto, boolean isUpdate) {
        this.recipeValidator.validateRecipeDto(recipeDto, this.validateProductsFromItems(recipeDto.getProdsDetail()));
        if (isNull(recipeDto.getId()) && !isUpdate) {
            recipeDto.setId(idGenerator.generateId());
        }
        recipeDto.getProdsDetail().forEach(prodDetailDto -> {
            if(isNull(prodDetailDto.getId()) && !isUpdate) {
                prodDetailDto.setId(idGenerator.generateId());
            } else if(isUpdate && isNull(prodDetailDto.getId())) {
                throw new CustomValidationException("Update recipe items, recipe item id cannot be null");
            }
        });

        Recipe recipe = recipeRepository.save(MAPPER.recipeDtoToRecipe(recipeDto));

        recipeDto.getProdsDetail().forEach(prodDetailDto -> {
            ProdDetail prodDetail = MAPPER.prodDetailDtoToProdDetail(prodDetailDto);
            prodDetail.setRecipe(recipe);
            //TODO double check for update Prod details, Ian is complaining cant pay attention
            prodDetailRepository.save(prodDetail);
        });
        return recipe;
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
    private List<Product> validateProductsFromItems(List<ProdDetailDto> recipeItems) {
        List<Product> products = recipeItems
                .stream()
                .map(prodDetailDto -> checkProduct(prodDetailDto.getProdId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
        return products;

    }

    private Optional<Product> checkProduct(String productId) {
        if(Objects.isNull(productId)) {
            throw  new CustomValidationException("Product id from ProdDetail cannot be null");
        }
        return productRepository.findById(productId);
    }
}
