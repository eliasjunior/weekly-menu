package com.weeklyMenu.useCases;

import com.weeklyMenu.domain.data.ProductDataAccess;
import com.weeklyMenu.domain.useCases.ManageProducts;
import com.weeklyMenu.dto.ProductDto;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * ProductTest
 */
public class ProductTest {

    @Test
    public void shouldSaveProduct() {
        ProductDto dto = new ProductDto();
        dto.setName("name");
     //   dto.setId(new Long(1));

        List<ProductDto> list = new ArrayList<>();
        list.add(dto);

        ProductDataAccess dataAccess = Mockito.mock(ProductDataAccess.class);
        when(dataAccess.getAllProducts()).thenReturn(list);

        ManageProducts store = new ManageProducts(dataAccess);

        store.create(dto);

//        GetProducts getProducts = new GetProducts(dataAccess);
//
//        assertEquals(1, getProducts.getAllProducts().size());
    }
}