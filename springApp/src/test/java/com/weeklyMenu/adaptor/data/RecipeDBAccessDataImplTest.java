//package com.weeklyMenu.useCase.data;
//
//import com.weeklyMenu.useCase.BaseIntegration;
//import com.weeklyMenu.data.dataSource.ProductRepository;
//import com.weeklyMenu.data.dataSource.RecipeRepository;
//import com.weeklyMenu.useCase.recipe.RecipeFactory;
//import com.weeklyMenu.data.mapper.RecipeMapper;
//import com.weeklyMenu.data.model.RecipeDB;
//import com.weeklyMenu.entity.Category;
//import com.weeklyMenu.entity.ProdDetail;
//import com.weeklyMenu.entity.Product;
//import com.weeklyMenu.entity.Recipe;
//import com.weeklyMenu.exceptions.CustomValidationException;
//import com.weeklyMenu.gateway.CategoryGateway;
//import com.weeklyMenu.gateway.RecipeGateway;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.weeklyMenu.useCase.recipe.RecipeFactory.createProdDetailsDto;
//import static com.weeklyMenu.useCase.recipe.RecipeFactory.createSingleProdDetailDto;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class RecipeDBAccessDataImplTest {
//    @Autowired
//    CategoryGateway categoryDataAccess;
//    @Autowired
//    RecipeGateway recipeAccessData;
//    @Autowired
//    private RecipeRepository recipeRepository;
//    @Autowired
//    private ProductRepository productRepository;
//
//    BaseIntegration baseIntegration;
//
//    @Before
//    public void setUp() {
//        baseIntegration = new BaseIntegration(categoryDataAccess, recipeRepository, productRepository);
//    }
//
//    @Test(expected = CustomValidationException.class)
//    public void try_to_create_recipe_with_no_prods() {
//        Recipe recipeToTest = new Recipe();
//        recipeToTest.setName("No Prod Recipe");
//
//        Recipe recipeCreated = recipeAccessData.create(recipeToTest);
//
//        assertNotNull(recipeCreated.getId());
//        assertEquals(2, recipeCreated.getProdsDetail().size());
//    }
//
//    @Test
//    public void create_recipe_with_prods() {
//        Category categoryDto = this.baseIntegration.createNewCategory();
//        Product prod1 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
//
//        Category categoryDto2 = this.baseIntegration.createNewCategory();
//        Product prod2 = this.baseIntegration.createNewProductGivenCategory(categoryDto2.getId());
//
//        List<ProdDetail> recipeItems = createProdDetailsDto(Arrays.asList(prod1, prod2));
//
//        Recipe recipeToTest = RecipeFactory.createRecipeDtoNoId(recipeItems);
//
//        recipeAccessData.create(recipeToTest);
//    }
//
//    @Test
//    public void update_recipe_items_adding_new_item() {
//        RecipeMapper recipeMapper = RecipeMapper.INSTANCE;
//        Category categoryDto = this.baseIntegration.createNewCategory();
//        Product prod1 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
//        Product prod2 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
//
//        List<ProdDetail> recipeItems = createProdDetailsDto(Arrays.asList(prod1, prod2));
//
//        Recipe recipeToTest = this.baseIntegration.createRecipeDto(recipeItems, "tete");
//
//        RecipeDB recipeJustCreated = recipeRepository.save(recipeMapper.recipeToRecipeDB(recipeToTest));
//
//        assertEquals(2, recipeJustCreated.getProdsDetail().size());
//
//        Product prod3 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
//        // Real testing bellow
//        ProdDetail detailDto = createSingleProdDetailDto(prod3);
//
//        recipeJustCreated.getProdsDetail().add(recipeMapper.prodDetailToProdDetailDB(detailDto));
//
//        recipeAccessData.edit(recipeMapper.recipeDBToRecipe(recipeJustCreated));
//
//        RecipeDB recipeCheck = recipeRepository.findById(recipeToTest.getId()).get();
//
//        assertEquals(3, recipeCheck.getProdsDetail().size());
//    }
//
//    @Test
//    public void update_recipe_items_removing_item() {
//        //cat 1 has 2 prod
//        Category categoryDto = this.baseIntegration.createNewCategory();
//        Product prod1 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
//        Product prodToBeRemoved = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
//
//        //cat 2 has 1 prod
//        Category categoryDto2 = this.baseIntegration.createNewCategory();
//        Product prod2 = this.baseIntegration.createNewProductGivenCategory(categoryDto2.getId());
//
//        List<ProdDetail> recipeItems = createProdDetailsDto(Arrays.asList(prod1, prod2, prodToBeRemoved));
//
//        Recipe recipeToTest = this.baseIntegration.createRecipeDto(recipeItems, "Apple juice");
//
//        assertEquals(3, recipeToTest.getProdsDetail().size());
//
//        // Real testing bellow
//        List<ProdDetail> recipeItemsChanged = recipeToTest.getProdsDetail()
//                .stream()
//                .filter(prodDetailDto -> !prodDetailDto.getProdId().equals(prodToBeRemoved.getId()))
//                .collect(Collectors.toList());
//
//        recipeToTest.setProdsDetail(recipeItemsChanged);
//
//        recipeAccessData.edit(recipeToTest);
//
//        RecipeDB recipeCheck = recipeRepository.findById(recipeToTest.getId()).get();
//
//        assertEquals(2, recipeCheck.getProdsDetail().size());
//    }
//
//    @Test
//    public void update_items_adding_another_prod() {
//        //TODO review here, it says update, but save ?
//        //cat 1 has 2 prod
//        Category categoryDto = this.baseIntegration.createNewCategory();
//        Product prod1 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
//        // add second item with another prod
//        Product prod2 = this.baseIntegration.createNewProductGivenCategory(categoryDto.getId());
//
//        List<ProdDetail> recipeItems = createProdDetailsDto(Arrays.asList(prod1, prod2));
//
//        Recipe recipeToTest = this.baseIntegration.createRecipeDto(recipeItems, "Milk");
//
//        //Test here
//        Recipe created = recipeAccessData.create(recipeToTest);
//
//        assertEquals(2, created.getProdsDetail().size());
//    }
//}
