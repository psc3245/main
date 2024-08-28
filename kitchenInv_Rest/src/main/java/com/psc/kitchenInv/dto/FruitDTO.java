package com.psc.kitchenInv.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.FruitClass;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FruitDTO {

    private Long id;

    private String name;

    private FruitClass fruitClass;

    private int age;

}
