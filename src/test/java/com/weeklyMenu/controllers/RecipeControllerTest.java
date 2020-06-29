package com.weeklyMenu.controllers;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.weeklyMenu.helpers.GlobalConstant;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.repository.CategoryRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static com.weeklyMenu.controllers.TestHelper.createCategory;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("test:application.properties")
public class RecipeControllerTest {
    @LocalServerPort
    int randomServerPort;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
    }

    @Test
    public void get_all_recipes() {
        given()
                .contentType(ContentType.JSON)
                .get(GlobalConstant.BASE_URL + "/recipes")
                .then()
                .statusCode(200);
    }

    @Test
    public void post_recipe() {
        Product product = productRepository.save(TestHelper.createProduct(createCategory(categoryRepository)));
        String recipeDto = "{\n" +
                "  \"name\": \"Orange Cake\"," +
                "  \"prodsDetail\": [\n" +
                        "{\n" +
                            "  \"prodId\": \""+product.getId() + "\"," +
                            "  \"quantity\": \"2\"" +
                        "}" +
                    "]" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(recipeDto)
                .post(GlobalConstant.BASE_URL + "/recipes")
                .then()
                .statusCode(201);
    }
}
