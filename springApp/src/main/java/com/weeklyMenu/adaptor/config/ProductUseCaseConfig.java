package com.weeklyMenu.adaptor.config;

import com.weeklyMenu.useCase.Interactor.product.FindProduct;
import com.weeklyMenu.useCase.Interactor.product.ManageProduct;
import com.weeklyMenu.useCase.Interactor.validator.ProductValidator;
import com.weeklyMenu.useCase.gateway.ProductGateway;
import com.weeklyMenu.useCase.generator.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCaseConfig {
    private final ProductGateway productGateway;
    private final ProductValidator productValidator;
    private final IdGenerator idGenerator;

    public ProductUseCaseConfig(ProductGateway productGateway, ProductValidator productValidator, IdGenerator idGenerator) {
        this.productGateway = productGateway;
        this.productValidator = productValidator;
        this.idGenerator = idGenerator;
    }
    @Bean
    public FindProduct createFindProduct() {
        return new FindProduct(productGateway);
    }

    @Bean
    public ManageProduct createManageProduct() {
        return new ManageProduct(productGateway, productValidator, idGenerator);
    }
}
