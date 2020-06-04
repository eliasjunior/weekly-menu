package com.weeklyMenu.vendor.config;

import com.weeklyMenu.vendor.helper.BasicGenerator;
import com.weeklyMenu.vendor.helper.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {

    @Bean
    public IdGenerator getIdGenerator() {
        return new BasicGenerator();
    }
}
