package com.weeklyMenu.adaptor.gateway;

import com.weeklyMenu.adaptor.SpringData.CategoryRepository;
import com.weeklyMenu.useCase.gateway.CategoryGateway;
import com.weeklyMenu.adaptor.model.CategoryDB;
import com.weeklyMenu.useCase.entity.Category;
import com.weeklyMenu.useCase.exceptions.CustomValidationException;
import com.weeklyMenu.adaptor.mapper.InventoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryGatewayImpl implements CategoryGateway {
    private final CategoryRepository categoryRepository;
    private final InventoryMapper mapper;

    public CategoryGatewayImpl(CategoryRepository categoryRepository, InventoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> cats = mapper.categoriesDBToCategories(categoryRepository.findAll());
        List<Category> withCatProds = cats
                .stream()
                .map(cat -> populateCatProds(cat))
                .collect(Collectors.toList());
        return withCatProds;
    }

    @Override
    public Category create(Category category) {
        CategoryDB dbMapper = mapper.categoryToCategoryDB(category);
        dbMapper.updateBasic(null);
        return mapper.categoryDBToCategory(categoryRepository.save(dbMapper));
    }

    @Override
    public void edit(Category category) {
        CategoryDB dbMapper = mapper.categoryToCategoryDB(category);
        CategoryDB oldCat = categoryRepository.findByName(dbMapper.getName());
        dbMapper.setBasicEntity(oldCat.getBasicEntity());
        dbMapper.updateBasic(oldCat.getBasicEntity());
        categoryRepository.save(dbMapper);
    }

    @Override
    public void remove(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategory(String id) {
        return mapper
                .categoryDBToCategory(categoryRepository.findById(id).get());
    }

    @Override
    public Category findByNameIgnoreCase(String name) {
        return mapper.categoryDBToCategory(categoryRepository.findByNameIgnoreCase(name));
    }

    @Override
    public Category findByNameIgnoreCaseAndIdIsDiff(String name, String id) {
        return mapper.categoryDBToCategory(categoryRepository.findByNameIgnoreCaseAndIdIsDiff(name, id));
    }

    @Override
    public Optional<Category> findById(String id) {
        Optional<CategoryDB> optional = categoryRepository.findById(id);
        return Optional.of(mapper.categoryDBToCategory(optional.get())) ;
    }

    private Category populateCatProds(Category cat) {
        try {
            List<String> catProds = cat.getProducts()
                    .stream()
                    .map(prod -> prod.getId())
                    .collect(Collectors.toList());
            cat.setProdIds(catProds);
            return cat;
        } catch (Exception e) {
            throw new CustomValidationException("Something went wrong while populating prod ids", e);
        }
    }
}
