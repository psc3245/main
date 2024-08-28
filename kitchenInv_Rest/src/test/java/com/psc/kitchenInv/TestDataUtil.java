package com.psc.kitchenInv;

import com.psc.kitchenInv.dto.FruitDTO;
import com.psc.kitchenInv.dto.VegetableDTO;
import util.*;

public class TestDataUtil {
    public TestDataUtil() {};

    public static Fruit buildFruitA() {
        return Fruit.builder()
                .name("apple")
                .id(1L)
                .fruitClass(FruitClass.APPLE_PEAR)
                .age(0)
                .build();
    }
    public static Fruit buildFruitB() {
        return Fruit.builder()
                .name("orange")
                .id(2L)
                .fruitClass(FruitClass.CITRUS)
                .age(4)
                .build();
    }
    public static Fruit buildFruitC() {
        return Fruit.builder()
                .name("peach")
                .id(3L)
                .fruitClass(FruitClass.STONE_FRUIT)
                .age(2)
                .build();
    }

    public static FruitDTO buildFruitDTO() {
        return FruitDTO.builder().name("watermelon")
                .id(4L)
                .fruitClass(FruitClass.MELON)
                .age(0)
                .build();
    }

    public static Vegetable buildVegA() {
        return Vegetable.builder()
                .name("onion")
                .id(1L)
                .vegClass(VegetableClass.ALLIUM)
                .age(0)
                .build();
    }

    public static Vegetable buildVegB() {
        return Vegetable.builder()
                .name("spinach")
                .id(2L)
                .vegClass(VegetableClass.LEAFY_GREEN)
                .age(2)
                .build();
    }

    public static Vegetable buildVegC() {
        return Vegetable.builder()
                .name("potato")
                .id(3L)
                .vegClass(VegetableClass.ROOT)
                .age(4)
                .build();
    }

    public static VegetableDTO buildVegetableDTO() {
        return VegetableDTO.builder().name("celery")
                .id(4L)
                .vegClass(VegetableClass.EDIBLE_STEM)
                .age(0)
                .build();
    }
}
