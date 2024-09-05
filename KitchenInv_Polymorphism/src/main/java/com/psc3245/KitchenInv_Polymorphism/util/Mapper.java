package com.psc3245.KitchenInv_Polymorphism.util;

import com.psc3245.KitchenInv_Polymorphism.CoreClasses.*;

public class Mapper {

    public static Food toFood(Food a) {
        return Food.builder()
                .id(a.getId())
                .category(a.getCategory().toString())
                .name(a.getName().toString())
                .presentation(a.getPresentation())
                .build();
    }

    public static Fruit toFruit(Food a) {
        return Fruit.builder()
                .id(a.getId())
                .category(FruitCategory.valueOf(a.getCategory()))
                .name(a.getName().toString())
                .presentation(a.getPresentation())
                .build();
    }

    public static Vegetable toVegetable(Food a) {
        return Vegetable.builder()
                .id(a.getId())
                .category(VegetableCategory.valueOf(a.getCategory()))
                .name(a.getName().toString())
                .presentation(a.getPresentation())
                .build();
    }
}
