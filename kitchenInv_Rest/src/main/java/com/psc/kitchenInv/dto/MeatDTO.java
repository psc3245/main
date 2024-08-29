package com.psc.kitchenInv.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.MeatClass;
import util.MeatType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MeatDTO {

    Long id;

    private String name;

    private MeatClass meatClass;

    private MeatType meatType;

    private int age;
}
