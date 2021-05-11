package main.java.com.weeklyMenu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.java.com.weeklyMenu.generator.IdGenerator;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String id;
    private String name;
    private List<ProdDetail> prodsDetail;
}
