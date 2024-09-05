package com.psc3245.KitchenInv_Polymorphism;


import com.psc3245.KitchenInv_Polymorphism.CoreClasses.Food;
import com.psc3245.KitchenInv_Polymorphism.CoreClasses.Fruit;
import com.psc3245.KitchenInv_Polymorphism.CoreClasses.FruitCategory;
import com.psc3245.KitchenInv_Polymorphism.util.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class QuickTest {

    @Test
    public void VerifyMapperWorks() {
        Fruit f = Fruit.builder()
                .id(1L)
                .name("Apple")
                .category(FruitCategory.APPLE_PEAR)
                .presentation("Whole")
                .build();
        Food a = Mapper.toFood(f);

        assertThat(a.getId().equals(f.getId()));
        assertThat(a.getName().equals(f.getName()));
        assertThat(a.getCategory().equals(f.getCategory().toString()));
        assertThat(a.getPresentation().equals(f.getPresentation()));
    }
}
