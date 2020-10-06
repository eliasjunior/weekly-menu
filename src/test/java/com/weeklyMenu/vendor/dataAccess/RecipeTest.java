package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.BaseIntegration;
import com.weeklyMenu.RecipeFactory;
import com.weeklyMenu.domain.data.CategoryDataAccess;
import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.domain.data.RecipeDataAccess;
import com.weeklyMenu.dto.CategoryDto;
import com.weeklyMenu.dto.ProdDetailDto;
import com.weeklyMenu.dto.ProductDto;
import com.weeklyMenu.dto.RecipeDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.model.Recipe;
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

import static com.weeklyMenu.RecipeFactory.createList;
import static com.weeklyMenu.RecipeFactory.createSingleProdDetailDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeTest {
    @Autowired
    CategoryDataAccess categoryDataAccess;
    @Autowired
    RecipeDataAccess recipeAccessData;
    @Autowired
    private ProductDataAccess productDataAccess;
    @Autowired
    private RecipeRepository recipeRepository;

    BaseIntegration baseIntegration;

    @Before
    public void setUp() {
        baseIntegration = new BaseIntegration(categoryDataAccess, productDataAccess, recipeRepository);
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
        CategoryDto categoryDto = this.baseIntegration.categoryFactory();
        ProductDto prod1 = this.baseIntegration.productFactory(categoryDto.getId());

        CategoryDto categoryDto2 = this.baseIntegration.categoryFactory();
        ProductDto prod2 = this.baseIntegration.productFactory(categoryDto2.getId());

        List<ProdDetailDto> recipeItems = createList(Arrays.asList(prod1, prod2));

        RecipeDto recipeToTest = RecipeFactory.createRecipeNoId(recipeItems);

        recipeAccessData.save(recipeToTest);
    }

    @Test
    public void update_recipe_items_adding_new_item() {
        CategoryDto categoryDto = this.baseIntegration.categoryFactory();
        ProductDto prod1 = this.baseIntegration.productFactory(categoryDto.getId());
        ProductDto prod2 = this.baseIntegration.productFactory(categoryDto.getId());
        ProductDto newProdTo = this.baseIntegration.productFactory(categoryDto.getId());

        List<ProdDetailDto> recipeItems = createList(Arrays.asList(prod1, prod2));

        RecipeDto recipeToTest = this.baseIntegration.createRecipe(recipeItems);

        assertEquals(2, recipeToTest.getProdsDetail().size());

        // Real testing bellow
        ProdDetailDto detailDto = createSingleProdDetailDto(newProdTo);

        List<ProdDetailDto> recipeItemsDto = recipeToTest.getProdsDetail();

        recipeItemsDto.add(detailDto);

        recipeToTest.setProdsDetail(recipeItemsDto);

        recipeAccessData.update(recipeToTest);

        Recipe recipeCheck = recipeRepository.findById(recipeToTest.getId()).get();

        assertEquals(3, recipeCheck.getProdsDetail().size());
    }

    @Test
    public void update_recipe_items_removing_item() {
        //cat 1 has 2 prod
        CategoryDto categoryDto = this.baseIntegration.categoryFactory();
        ProductDto prod1 = this.baseIntegration.productFactory(categoryDto.getId());
        ProductDto prodToBeRemoved = this.baseIntegration.productFactory(categoryDto.getId());

        //cat 2 has 1 prod
        CategoryDto categoryDto2 = this.baseIntegration.categoryFactory();
        ProductDto prod2 = this.baseIntegration.productFactory(categoryDto2.getId());

        List<ProdDetailDto> recipeItems = createList(Arrays.asList(prod1, prod2, prodToBeRemoved));

        RecipeDto recipeToTest = this.baseIntegration.createRecipe(recipeItems);

        assertEquals(3, recipeToTest.getProdsDetail().size());

        // Real testing bellow
        List<ProdDetailDto> recipeItemsChanged = recipeToTest.getProdsDetail()
                .stream()
                .filter(prodDetailDto -> prodDetailDto.getProdId() != prodToBeRemoved.getId())
                .collect(Collectors.toList());

        recipeToTest.setProdsDetail(recipeItemsChanged);

        recipeAccessData.update(recipeToTest);

        Recipe recipeCheck = recipeRepository.findById(recipeToTest.getId()).get();

        assertEquals(2, recipeCheck.getProdsDetail().size());
    }

    @Test
    public void update_items_adding_another_prod() {
        //cat 1 has 2 prod
        CategoryDto categoryDto = this.baseIntegration.categoryFactory();
        ProductDto prod1 = this.baseIntegration.productFactory(categoryDto.getId());

        List<ProdDetailDto> recipeItems = createList(Arrays.asList(prod1));

        RecipeDto recipeToTest = this.baseIntegration.createRecipe(recipeItems);

        // add second item with another prod
        ProductDto prod2 = this.baseIntegration.productFactory(categoryDto.getId());
        ProdDetailDto secondDetailDto = createSingleProdDetailDto(prod2);

        recipeToTest.getProdsDetail().add(secondDetailDto);

        RecipeDto created = recipeAccessData.save(recipeToTest);

        assertEquals(2, created.getProdsDetail().size());
    }
}
