package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.inventory.domain.data.CategoryDataAccess;
import com.weeklyMenu.inventory.dto.CategoryDTO;
import com.weeklyMenu.inventory.dto.ProductDTO;
import com.weeklyMenu.mapper.InventoryMapper;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.model.Category;
import com.weeklyMenu.vendor.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryAccessImpl implements CategoryDataAccess {
    private CategoryRepository categoryRepository;
    private final InventoryMapper MAPPER = InventoryMapper.INSTANCE;
    private IdGenerator idGenerator;

    public CategoryAccessImpl(CategoryRepository categoryRepository, IdGenerator idGenerator) {
        this.categoryRepository = categoryRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return MAPPER.categoryToCategoryDto(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == null) {
            categoryDTO.setId(idGenerator.generateId());
        }

        //TODO move this later
        if (categoryDTO.getCatProds() != null && categoryDTO.getCatProds().size() > 0) {
            categoryDTO.setProducts(categoryDTO.getCatProds()
                    .stream()
                    .map(prodId -> new ProductDTO(prodId))
                    .collect(Collectors.toList()));
        }

        return MAPPER.categoryToCategoryDto(categoryRepository.save(MAPPER.categoryDtoToCategory(categoryDTO)));
    }

    @Override
    public void update(CategoryDTO dto) {
        Optional<Category> optional = categoryRepository.findById(dto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Category not found to update", new RuntimeException());
        }
        categoryRepository.save(MAPPER.categoryDtoToCategory(dto));
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getCategory(String id) {
        return MAPPER
                .categoryToCategoryDto(categoryRepository.findById(id).get());
    }

    @Override
    public boolean isCategoryNameUsed(CategoryDTO dto) {
        Category category = categoryRepository.findByName(dto.getName());
        return category != null;
    }
}
