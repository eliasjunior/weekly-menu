//package com.weeklyMenu.useCase.controllers;
//
//import com.jayway.restassured.RestAssured;
//import com.jayway.restassured.http.ContentType;
//import com.weeklyMenu.data.dataSource.CategoryRepository;
//import com.weeklyMenu.useCase.common.GlobalConstant;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static com.jayway.restassured.RestAssured.given;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@PropertySource("test:application.properties")
//public class CategoryDBControllerTest {
//    @LocalServerPort
//    int randomServerPort;
//
//    @Before
//    public void setup() {
//        RestAssured.port = randomServerPort;
//    }
//
//    @Test
//    public void get_all_categories() {
//        given()
//                .contentType(ContentType.JSON)
//                .get(GlobalConstant.BASE_URL + "/categories")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    public void post_category() {
//        String categoryDto = "{\n" +
//                "  \"name\": \"Norris\"" +
//                "}";
//        given()
//                .contentType(ContentType.JSON)
//                .body(categoryDto)
//                .post(GlobalConstant.BASE_URL + "/categories")
//                .then()
//                .statusCode(200);
//    }
//}
