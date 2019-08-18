package com.weeklyMenu.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import static com.jayway.restassured.RestAssured.when;

import com.jayway.restassured.RestAssured;
/**
 * ProductControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest{
  @LocalServerPort
  int randomServerPort;

  @Before
  public void setup() {
    RestAssured.port = randomServerPort;
  }

  @Test
  public void get_all_products() {
    when()
    .get("/weeklymenu/v1/product")
    .then()
    .statusCode(200);
  }
  
}