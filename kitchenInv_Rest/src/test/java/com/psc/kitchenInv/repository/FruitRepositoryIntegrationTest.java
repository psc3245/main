package com.psc.kitchenInv.repository;

import com.psc.kitchenInv.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.psc.kitchenInv.Fruit;
import util.FruitClass;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FruitRepositoryIntegrationTest {


    private FruitRepository underTest;

    @Autowired
    public FruitRepositoryIntegrationTest(FruitRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatFruitCanBeCreatedAndRecalled() {
        Fruit f = TestDataUtil.buildFruitA();
        underTest.save(f);
        Optional<Fruit> result = underTest.findById(f.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(f);
    }

    @Test
    public void testThatManyFruitsCanBeCreatedAndRecalled() {
        Fruit f1 = TestDataUtil.buildFruitA();
        underTest.save(f1);
        Fruit f2 = TestDataUtil.buildFruitB();
        underTest.save(f2);
        Fruit f3 = TestDataUtil.buildFruitC();
        underTest.save(f3);


        Iterable<Fruit> results = underTest.findAll();

        assertThat(results).hasSize(3);
        assertThat(results).containsExactly(f1, f2, f3);
    }

    @Test
    public void testThatFruitCanBeUpdated() {
        Fruit f = TestDataUtil.buildFruitA();
        underTest.save(f);
        f.setName("updated");
        underTest.save(f);
        Optional<Fruit> result = underTest.findById(f.getId());
        assertThat(result.get()).isEqualTo(f);
    }

    @Test
    public void testThatFruitCanBeDeleted() {
        Fruit f = TestDataUtil.buildFruitA();
        underTest.save(f);

        underTest.delete(f);
        Optional<Fruit> result = underTest.findById(f.getId());
        assertThat(result).isEmpty();
    }

//    @Test
//    public void testComplexQuery() {
//        Fruit f1 = TestDataUtil.buildFruitA();
//        underTest.save(f1);
//        Fruit f2 = TestDataUtil.buildFruitB();
//        underTest.save(f2);
//        Fruit f3 = TestDataUtil.buildFruitC();
//        underTest.save(f3);
//
//        Iterable<Fruit> results = underTest.fruitClassEquals(FruitClass.APPLE_PEAR);
//        assertThat(results).containsExactly(f1);
//    }

}
