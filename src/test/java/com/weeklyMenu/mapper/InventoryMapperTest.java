package com.weeklyMenu.mapper;

import com.weeklyMenu.inventory.dto.ProductDTO;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;


public class InventoryMapperTest {

    @Test
    public void entityToDTO() {
    }

    @Test
    public void testDtoToEntityBeConverted() {
        ProductDTO dto = new ProductDTO();
       // dto.setId(1l);
        dto.setName("Norris");

        InventoryMapper.INSTANCE.productDtoToProduct(dto);

        assertNotNull(dto);
//        assertThat( carDto.getMake() ).isEqualTo( "Morris" );
//        assertThat( carDto.getSeatCount() ).isEqualTo( 5 );
//        assertThat( carDto.getType() ).isEqualTo( "SEDAN" );

    }

    @Test
    public void testEntityToDTO() {
    }
}
