package com.weeklyMenu.dto;

import com.weeklyMenu.vendor.helper.IdGenerator;
import lombok.Data;
import java.util.List;

import static java.util.Objects.isNull;

@Data
public class RecipeDto {
    private String id;
    private String name;
    private List<ProdDetailDto> prodsDetail;

    public void generateItemsIds(IdGenerator idGenerator) {
        this.getProdsDetail().forEach(prodDetailDto -> {
            if (isNull(prodDetailDto.getId()) || prodDetailDto.getId().isEmpty()) {
                //TODO need a test for regression here, as I keep forgetting,
                //TODO if its update and there is not ID means could have a new prod checked
                prodDetailDto.setId(idGenerator.generateId());
            }
        });
    }
}
