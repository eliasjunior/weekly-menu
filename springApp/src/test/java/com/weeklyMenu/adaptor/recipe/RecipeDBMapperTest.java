//package com.weeklyMenu.useCase.recipe;
//
//import com.weeklyMenu.entity.ProdDetail;
//import com.weeklyMenu.entity.Recipe;
//import com.weeklyMenu.data.mapper.RecipeMapper;
//import com.weeklyMenu.data.model.ProdDetailDB;
//import com.weeklyMenu.data.model.ProductDB;
//import com.weeklyMenu.data.model.RecipeDB;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//public class RecipeDBMapperTest {
//    @Test
//    public void testMapperToEntity() {
//        RecipeMapper recipeMapper = RecipeMapper.INSTANCE;
//
//        Recipe recipe = new Recipe();
//        recipe.setId(UUID.randomUUID().toString());
//        recipe.setName("Greek Salad");
//
//        ProdDetail prodDetail = new ProdDetail();
//        prodDetail.setId("1");
//        prodDetail.setQuantity(2);
//        prodDetail.setProdId("prod1");
//
//        List<ProdDetail> prodsDetail = new ArrayList<>();
//        prodsDetail.add(prodDetail);
//
//        recipe.setProdsDetail(prodsDetail);
//
//        System.out.println("#################");
//        System.out.println(recipeMapper.recipeToRecipeDB(recipe));
//    }
//
//    @Test
//    public void testMapperEntityTo() {
//        RecipeMapper recipeMapper = RecipeMapper.INSTANCE;
//
//        RecipeDB recipe = new RecipeDB();
//        recipe.setId(UUID.randomUUID().toString());
//        recipe.setName("Greek Salad");
//
//        ProdDetailDB prodDetailDB = new ProdDetailDB();
//        prodDetailDB.setId("1");
//        prodDetailDB.setQuantity(2);
//       // prodDetail.setProdId("prod1");
//
//        ProductDB product = new ProductDB();
//        product.setId("prod1");
//        prodDetailDB.setProduct(product);
//
//        List<ProdDetailDB> prodsDetail = new ArrayList<>();
//        prodsDetail.add(prodDetailDB);
//
//        recipe.setProdsDetail(prodsDetail);
//
//        System.out.println("#################");
//        System.out.println(recipeMapper.recipeDBToRecipe(recipe));
//    }
//
//}
