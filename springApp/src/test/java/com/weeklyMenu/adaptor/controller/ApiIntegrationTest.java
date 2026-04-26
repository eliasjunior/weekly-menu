package com.weeklyMenu.adaptor.controller;

import com.weeklyMenu.adaptor.springData.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void categoryCanBeCreatedListedAndDeleted() throws Exception {
        String categoryName = uniqueName("Vegetables");
        String categoryId = createCategory(categoryName);

        mvc.perform(get("/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItem(categoryId)))
                .andExpect(jsonPath("$[*].name", hasItem(categoryName)));

        mvc.perform(delete("/v1/categories/{id}", categoryId))
                .andExpect(status().isNoContent());

        assertFalse(categoryRepository.findById(categoryId).isPresent());
    }

    @Test
    void productCanBeCreatedAndListedForCategory() throws Exception {
        String productName = uniqueName("Apple");
        String categoryId = createCategory(uniqueName("Fruit"));

        mvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "quantityType": "unit",
                                  "catId": "%s"
                                }
                                """.formatted(productName, categoryId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(productName))
                .andExpect(jsonPath("$.catId").value(categoryId));

        mvc.perform(get("/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", hasItem(productName)));
    }

    @Test
    void recipeCanBeCreatedAndListedForProduct() throws Exception {
        String recipeName = uniqueName("Bread");
        String categoryId = createCategory(uniqueName("Bakery"));
        String productId = createProduct(categoryId, uniqueName("Flour"));

        mvc.perform(post("/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "prodsDetail": [
                                    {
                                      "prodId": "%s",
                                      "quantity": 2
                                    }
                                  ]
                                }
                                """.formatted(recipeName, productId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(recipeName))
                .andExpect(jsonPath("$.prodsDetail", hasSize(1)))
                .andExpect(jsonPath("$.prodsDetail[0].prodId").value(productId));

        mvc.perform(get("/v1/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", hasItem(recipeName)));
    }

    private String createCategory(String name) throws Exception {
        return mvc.perform(post("/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s"
                                }
                                """.formatted(name)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(name))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
    }

    private String createProduct(String categoryId, String name) throws Exception {
        return mvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "quantityType": "unit",
                                  "catId": "%s"
                                }
                                """.formatted(name, categoryId)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
    }

    private String uniqueName(String prefix) {
        return prefix + " " + UUID.randomUUID();
    }
}
