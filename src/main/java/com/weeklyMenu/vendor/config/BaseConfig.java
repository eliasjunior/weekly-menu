package com.weeklyMenu.vendor.config;

import com.weeklyMenu.vendor.dataAccess.InventoryValidator;
import com.weeklyMenu.vendor.dataAccess.RecipeValidator;
import com.weeklyMenu.vendor.helper.BasicGenerator;
import com.weeklyMenu.vendor.helper.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BaseConfig {

    @Bean
    public IdGenerator getIdGenerator() {
        return new BasicGenerator();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                final String HOST_ALLOWED = "http://localhost:3001";
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins(HOST_ALLOWED);
            }
        };
    }

    @Bean
    public RecipeValidator createRecipeValidator() {
        return new RecipeValidator();
    }

    @Bean
    public InventoryValidator createInventoryValidator() {
        return new InventoryValidator();
    }
}
