package com.weeklyMenu.vendor.dataAccess;

import com.weeklyMenu.domain.data.CartAccessData;
import com.weeklyMenu.dto.CartDto;
import com.weeklyMenu.dto.ProductItemDto;
import com.weeklyMenu.exceptions.CustomValidationException;
import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.helper.IdGenerator;
import com.weeklyMenu.vendor.model.Cart;
import com.weeklyMenu.vendor.model.Product;
import com.weeklyMenu.vendor.model.ProductItem;
import com.weeklyMenu.vendor.model.Recipe;
import com.weeklyMenu.vendor.repository.ProductItemRepository;
import com.weeklyMenu.vendor.repository.CartRepository;
import com.weeklyMenu.vendor.repository.ProductRepository;
import com.weeklyMenu.vendor.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartAccessDataImpl implements CartAccessData {
    private final CartRepository repository;
    private final ProductItemRepository itemRepository;
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private final CartMapper MAPPER = CartMapper.INSTANCE;
    private IdGenerator idGenerator;

    public CartAccessDataImpl(CartRepository repository,
                              ProductItemRepository itemRepository,
                              RecipeRepository recipeRepository,
                              ProductRepository productRepository,
                              IdGenerator idGenerator) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<CartDto> getCartList() {
        List<Cart> listDto = repository.findAll();
        listDto.forEach(cart -> {
            System.out.println(cart.getProductItems());
        });
        return MAPPER.cartToDto(listDto);
    }

    @Override
    public CartDto save(CartDto dto) {
        if (dto.getId() == null) {
            dto.setId(idGenerator.generateId());
            dto.setProductItems(generateIdProdItem(dto.getProductItems()));
        }

        Cart cart = MAPPER.dtoToCart(dto);
        cart.getProductItems().forEach(productItem -> {
            productItem.setRecipes(fillRecipe(productItem.getRecipes(), productItem));
            productItem.setProduct(fillProduct(productItem.getProduct()));
            productItem.setCart(cart);
        });

        Cart cartNew = repository.save(cart);

        return MAPPER.cartToDto(cartNew);
    }

    @Override
    public void update(CartDto dto) {
        Optional<Cart> optional = repository.findById(dto.getId());
        if (!optional.isPresent()) {
            throw new CustomValidationException("Shopping list not found to update", new RuntimeException());
        }

        Cart shoppingList = MAPPER.dtoToCart(dto);

//        shoppingList.getProductItems()
//                .stream()
//                .map(productItem -> {
//                    Optional<ProductItem> opt = itemRepository.findById(productItem.getId());
//
//                });

        repository.save(shoppingList);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    private List<ProductItemDto> generateIdProdItem(List<ProductItemDto> dtoItems) {
        if(dtoItems == null) {
            throw new CustomValidationException("There is not product item to save the cart");
        }
        return dtoItems
            .stream()
            .map(productItemDto -> {
                productItemDto.setId(idGenerator.generateId());
                return productItemDto;
            })
            .collect(Collectors.toList());
    }

    private List<Recipe> fillRecipe(List<Recipe> recipes, ProductItem productItem) {
        return recipes.stream().map(recipe -> {
            Optional<Recipe> optional = recipeRepository.findById(recipe.getId());
            if(optional.isPresent()) {
                Recipe recIn =  optional.get();
                recIn.setProductItem(productItem);
                return recIn;
            } else {
                String msgError = "Attempt to save productItem but recipe does not exist, id="+ recipe.getId();
                throw new CustomValidationException(msgError);
            }
        }).collect(Collectors.toList());
    }

    private Product fillProduct(Product product) {
        Optional<Product> prodIn =  productRepository.findById(product.getId());
        if(prodIn.isPresent()) {
            return  prodIn.get();
        } else {
            String msgError = "Attempt to save productItem but product does not, exist id="+ product.getId();
            throw new CustomValidationException(msgError);
        }
    }

}
