package com.weeklyMenu.webAdaptor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
}
