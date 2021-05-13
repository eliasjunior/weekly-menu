package com.weeklyMenu.adaptor.config;

import com.weeklyMenu.adaptor.mapper.CartItemMapper;
import com.weeklyMenu.adaptor.mapper.CartMapper;
import com.weeklyMenu.adaptor.mapper.InventoryMapper;
import com.weeklyMenu.adaptor.mapper.RecipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class BaseConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                final String [] HOSTS_ALLOWED = {"http://localhost:3001", "https://weekly-menu-ui.herokuapp.com"};
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins(HOSTS_ALLOWED);
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
        return InventoryMapper.INSTANCE;
    }
    @Bean
    public CartMapper getCartMapper() {
        return CartMapper.INSTANCE;
    }
    @Bean
    public RecipeMapper getRecipeMapper() {
        return RecipeMapper.INSTANCE;
    }
}
