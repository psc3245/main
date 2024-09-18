package com.psc3245.KitchenInv_Polymorphism.CoreClasses;


import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
public class Vegetable extends Food{

    private Long id;

    private VegetableCategory category;

    private String name;

    private String presentation;

}
