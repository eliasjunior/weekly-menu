package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.BaseIntegration;
import com.weeklyMenu.RecipeFactory;
import com.weeklyMenu.vendor.model.RecipeDB;
import main.java.com.weeklyMenu.useCase.data.CategoryDataAccess;
import main.java.com.weeklyMenu.useCase.data.ProductDataAccess;
import main.java.com.weeklyMenu.useCase.data.RecipeDataAccess;
import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;
import main.java.com.weeklyMenu.useCase.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.mapper.RecipeMapper;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.weeklyMenu.RecipeFactory.createProdDetailsDto;
import static com.weeklyMenu.RecipeFactory.createSingleProdDetailDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeDBAccessDataImplTest {
    @Autowired
    CategoryDataAccess categoryDataAccess;
    @Autowired
    RecipeDataAccess recipeAccessData;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ProductRepository productRepository;

    BaseIntegration baseIntegration;

    @Before
    public void setUp() {
        baseIntegration = new BaseIntegration(categoryDataAccess, recipeRepository, productRepository);
    }

    @Test(expected = CustomValidationException.class)
    public void try_to_create_recipe_with_no_prods() {
        RecipeDto recipeToTest = new RecipeDto();
        recipeToTest.setName("No Prod Recipe");

        RecipeDto recipeCreated = recipeAccessData.save(recipeToTest);

        assertNotNull(recipeCreated.getId());
        assertEquals(2, recipeCreated.getProdsDetail().size());
    }

    @Test
    public void create_recipe_with_prods() {
        CategoryDto categoryDto = this.baseIntegration.createNewCategory();
        ProductDto prod1 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());

        CategoryDto categoryDto2 = this.baseIntegration.createNewCategory();
        ProductDto prod2 = this.baseIntegration.createNewProductGivenCategory(categoryDto2.getId());

        List<ProdDetailDto> recipeItems = createProdDetailsDto(Arrays.asList(prod1, prod2));

        RecipeDto recipeToTest = RecipeFactory.createRecipeDtoNoId(recipeItems);

        recipeAccessData.save(recipeToTest);
    }

    @Test
    public void update_recipe_items_adding_new_item() {
        RecipeMapper recipeMapper = RecipeMapper.INSTANCE;
        CategoryDto categoryDto = this.baseIntegration.createNewCategory();
        ProductDto prod1 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
        ProductDto prod2 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());

        List<ProdDetailDto> recipeItems = createProdDetailsDto(Arrays.asList(prod1, prod2));

        RecipeDto recipeToTest = this.baseIntegration.createRecipeDto(recipeItems, "tete");

        RecipeDB recipeJustCreated = recipeRepository.save(recipeMapper.recipeDtoToRecipe(recipeToTest));

        assertEquals(2, recipeJustCreated.getProdsDetail().size());

        ProductDto prod3 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
        // Real testing bellow
        ProdDetailDto detailDto = createSingleProdDetailDto(prod3);

        recipeJustCreated.getProdsDetail().add(recipeMapper.prodDetailDtoToProdDetail(detailDto));

        recipeAccessData.update(recipeMapper.recipeToRecipeDto(recipeJustCreated));

        RecipeDB recipeCheck = recipeRepository.findById(recipeToTest.getId()).get();

        assertEquals(3, recipeCheck.getProdsDetail().size());
    }

    @Test
    public void update_recipe_items_removing_item() {
        //cat 1 has 2 prod
        CategoryDto categoryDto = this.baseIntegration.createNewCategory();
        ProductDto prod1 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
        ProductDto prodToBeRemoved = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());

        //cat 2 has 1 prod
        CategoryDto categoryDto2 = this.baseIntegration.createNewCategory();
        ProductDto prod2 = this.baseIntegration.createNewProductGivenCategory(categoryDto2.getId());

        List<ProdDetailDto> recipeItems = createProdDetailsDto(Arrays.asList(prod1, prod2, prodToBeRemoved));

        RecipeDto recipeToTest = this.baseIntegration.createRecipeDto(recipeItems, "Apple juice");

        assertEquals(3, recipeToTest.getProdsDetail().size());

        // Real testing bellow
        List<ProdDetailDto> recipeItemsChanged = recipeToTest.getProdsDetail()
                .stream()
                .filter(prodDetailDto -> prodDetailDto.getProdId() != prodToBeRemoved.getId())
                .collect(Collectors.toList());

        recipeToTest.setProdsDetail(recipeItemsChanged);

        recipeAccessData.update(recipeToTest);

        RecipeDB recipeCheck = recipeRepository.findById(recipeToTest.getId()).get();

        assertEquals(2, recipeCheck.getProdsDetail().size());
    }

    @Test
    public void update_items_adding_another_prod() {
        //TODO review here, it says update, but save ?
        //cat 1 has 2 prod
        CategoryDto categoryDto = this.baseIntegration.createNewCategory();
        ProductDto prod1 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
        // add second item with another prod
        ProductDto prod2 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());

        List<ProdDetailDto> recipeItems = createProdDetailsDto(Arrays.asList(prod1, prod2));

        RecipeDto recipeToTest = this.baseIntegration.createRecipeDto(recipeItems, "Milk");

        //Test here
        RecipeDto created = recipeAccessData.save(recipeToTest);

        assertEquals(2, created.getProdsDetail().size());
    }
}
