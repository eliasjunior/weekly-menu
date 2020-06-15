package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.domain.data.RecipeDataAccess;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeAccessDataImpl implements RecipeDataAccess {
    private final RecipeRepository recipeRepository;
    private final ProdDetailRepository prodDetailRepository;
    private final ProductRepository productRepository;
    private IdGenerator idGenerator;
    private final RecipeMapper MAPPER = RecipeMapper.INSTANCE;

    public RecipeAccessDataImpl(RecipeRepository recipeRepository,
                                ProdDetailRepository prodDetailRepository, ProductRepository productRepository, IdGenerator idGenerator) {
        this.recipeRepository = recipeRepository;
        this.prodDetailRepository = prodDetailRepository;
        this.productRepository = productRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return MAPPER.recipesToRecipesDto(recipes);
    }

    @Override
    public RecipeDto save(RecipeDto recipeDto) {
        if (recipeDto.getId() == null) {
            recipeDto.setId(idGenerator.generateId());
            recipeDto.getProdsDetail().forEach(prodDetailDto -> {
                prodDetailDto.setId(idGenerator.generateId());
            });
        }

        Recipe recipe = recipeRepository.save(MAPPER.recipeDtoToRecipe(recipeDto));

        recipeDto.getProdsDetail().forEach(prodDetailDto -> {
            ProdDetail prodDetail = MAPPER.prodDetailDtoToProdDetail(prodDetailDto);
            prodDetail.setRecipe(recipe);

            Optional<Product> product = productRepository.findById(prodDetail.getProdId());
            if(product.isPresent()) {
                prodDetailRepository.save(prodDetail);
            } else {
                String msgError = "Attempt to save Prod Detail of recipe "+ recipe.getName() +"but product does not exist";
                throw new CustomValidationException(msgError);
            }
        });
        return MAPPER.recipeToRecipeDto(recipe);
    }

    @Override
    public void update(RecipeDto dto) {
        Optional<Recipe> optional = recipeRepository.findById(dto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Recipe not found to update", new RuntimeException());
        }
        recipeRepository.save(MAPPER.recipeDtoToRecipe(dto));
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
}
