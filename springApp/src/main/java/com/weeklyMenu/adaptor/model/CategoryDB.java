package com.weeklyMenu.adaptor.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CATEGORY")
public class CategoryDB {
    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @NotNull
    @Column(name = "NAME", unique=true)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductDB> products;

    @Embedded
    @Builder.Default
    private BasicEntity basicEntity = new BasicEntity();

    public void updateBasic(BasicEntity basicEntity) {
        this.getBasicEntity().updateBasic(basicEntity);
    }
}
