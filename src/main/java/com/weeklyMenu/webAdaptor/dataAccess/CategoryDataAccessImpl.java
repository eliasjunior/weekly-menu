package com.weeklyMenu.webAdaptor.dataAccess;

import com.weeklyMenu.webAdaptor.model.CategoryDB;
import main.java.com.weeklyMenu.entity.Category;
import main.java.com.weeklyMenu.exceptions.CustomValidationException;
import main.java.com.weeklyMenu.gateway.CategoryGateway;
import com.weeklyMenu.webAdaptor.mapper.InventoryMapper;
import com.weeklyMenu.webAdaptor.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryDataAccessImpl implements CategoryGateway {
    private CategoryRepository categoryRepository;
    private final InventoryMapper MAPPER = InventoryMapper.INSTANCE;

    public CategoryDataAccessImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> catsDto = MAPPER.categoriesDBToCategories(categoryRepository.findAll());
        List<Category> withCatProds = catsDto
                .stream()
                .map(cat -> populateCatProds(cat))
                .collect(Collectors.toList());
        return withCatProds;
    }

    @Override
    public Category create(Category category) {
        CategoryDB dbMapper = MAPPER.categoryToCategoryDB(category);
        dbMapper.updateBasic(null);
        return MAPPER.categoryDBToCategory(categoryRepository.save(dbMapper));
    }

    @Override
    public void edit(Category category) {
        CategoryDB dbMapper = MAPPER.categoryToCategoryDB(category);
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
        return MAPPER
                .categoryDBToCategory(categoryRepository.findById(id).get());
    }

    @Override
    public Category findByNameIgnoreCase(String name) {
        return MAPPER.categoryDBToCategory(categoryRepository.findByNameIgnoreCase(name));
    }

    @Override
    public Category findByNameIgnoreCaseAndIdIsDiff(String name, String id) {
        return MAPPER.categoryDBToCategory(categoryRepository.findByNameIgnoreCaseAndIdIsDiff(name, id));
    }

    @Override
    public Optional<Category> findById(String id) {
        Optional<CategoryDB> optional = categoryRepository.findById(id);
        return Optional.of(MAPPER.categoryDBToCategory(optional.get())) ;
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
