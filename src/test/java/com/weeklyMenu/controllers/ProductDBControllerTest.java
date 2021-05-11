package com.weeklyMenu.controllers;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.weeklyMenu.helpers.GlobalConstant;
import com.weeklyMenu.vendor.model.CategoryDB;
import com.weeklyMenu.vendor.model.ProductDB;
import com.weeklyMenu.vendor.repository.CategoryRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import org.junit.Assert;
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

/**
 * ProductControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("test:application.properties")
public class ProductDBControllerTest {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
    }

    @Test
    public void post_product_and_verify() {
        CategoryDB category = createCategory(categoryRepository);
        String productDto = "{\n" +
                "  \"name\": \"Chuck\"," +
                "  \"quantityType\": \"u\"," +
                "  \"catId\": \""+category.getId() + "\"" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(productDto)
                .post(GlobalConstant.BASE_URL + "/products")
                .then()
                .statusCode(201)
                .assertThat();
    }

    @Test
    public void get_all_products() {
        given()
                .contentType(ContentType.JSON)
                .get(GlobalConstant.BASE_URL + "/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void put_product_and_verify() {
        CategoryDB category = createCategory(categoryRepository);
        ProductDB product = productRepository.save(TestHelper.createProduct(category));

        String productDto = "{\n" +
                "  \"name\": \"Van Damme\"," +
                "  \"id\": \""+product.getId() + "\"," +
                "  \"quantityType\": \"u\"," +
                "  \"catId\": \""+category.getId() + "\"" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(productDto)
                .put(GlobalConstant.BASE_URL + "/products")
                .then()
                .statusCode(204)
                .assertThat();


        ProductDB product1 = productRepository.findByName("Van Damme");

        Assert.assertEquals(product1.getName(), "Van Damme");

    }

}