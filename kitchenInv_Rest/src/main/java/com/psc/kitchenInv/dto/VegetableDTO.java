package com.psc.kitchenInv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.FruitClass;
import util.VegetableClass;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VegetableDTO {

    private Long id;

    private String name;

    private VegetableClass vegClass;

    private int age;
}
