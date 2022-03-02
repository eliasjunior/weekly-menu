package com.weeklyMenu.adaptor.config;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

import com.weeklyMenu.adaptor.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BaseConfig {
    @Value("${client_cors_url.allowed_hosts}")
    private URL[] hosts;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins(Arrays.stream(hosts)
                        .filter(url -> !Objects.isNull(url))
                        .map(url -> url.toString()).toArray(String[]::new));
            }
        };
    }

    @Bean
    public CartItemMapper createItemMapper() {
        return new CartItemMapper();
    }

    @Bean
    @Scope("prototype")
    public Logger logger(final InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
    }

    @Bean
    public InventoryMapper getInventoryMapper() {
        return new InventoryMapperImpl();
    }

    @Bean
    public CartMapper getCartMapper() {
        return new CartMapperImpl();
    }

    @Bean
    public RecipeMapper getRecipeMapper() {
        return new RecipeMapperImpl();
    }
}
