package com.weeklyMenu.domain.data;

import com.weeklyMenu.domain.mapper.ProductItemMapper;
import com.weeklyMenu.domain.useCases.ProductItemDomain;
import com.weeklyMenu.vendor.mapper.CartMapper;
import com.weeklyMenu.vendor.model.CartItem;
import com.weeklyMenu.vendor.repository.ProductItemRepository;

import java.util.List;

public class ProductItemDAImpl implements ProductItemDataAccess {
    private ProductItemRepository productItemRepository;
    private ProductItemDomain productItemDomain;
    CartMapper MAPPER = CartMapper.INSTANCE;

    public ProductItemDAImpl(ProductItemDomain productItemDomain) {
        //this.productItemRepository = productItemRepository;
        this.productItemDomain = productItemDomain;
    }


    @Override
    public List<ProductItemMapper> getProductItems() {
        List<CartItem> items = productItemRepository.findAll();
       // List<ProductItemMapper> listItems = MAPPER.entityToDomainMapperTo(items);

       // return productItemDomain.fillWithItems(listItems);
        return null;
    }
}
