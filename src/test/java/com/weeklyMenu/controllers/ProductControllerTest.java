package com.weeklyMenu.controllers;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.weeklyMenu.helpers.GlobalConstant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * ProductControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @LocalServerPort
    int randomServerPort;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
    }

    @Test
    public void get_all_products() {
        String payload = "{\n" +
                "  \"name\": \"Chuck\"" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(GlobalConstant.BASE_URL + "/product").then().statusCode(200);

        when()
                .get(GlobalConstant.BASE_URL + "/product")
                .then()
                .statusCode(200).assertThat().body("name", hasItems("Chuck"));
    }

}