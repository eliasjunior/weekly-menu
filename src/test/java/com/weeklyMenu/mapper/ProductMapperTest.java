package com.weeklyMenu.mapper;
/**
 * ProductMapperTest
 */

import static org.junit.Assert.assertEquals;

import com.weeklyMenu.inventory.dto.ProductDTO;

import org.junit.Test;

public class ProductMapperTest {

    @Test
    public void shouldConvertDtoToEntity() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Ema");
        dto.setId(123l);

        ProductMapper.dtoToEntity(dto);
        assertEquals(dto.getName(), "Ema");
        assertEquals(dto.getId(), "123");
    }
}