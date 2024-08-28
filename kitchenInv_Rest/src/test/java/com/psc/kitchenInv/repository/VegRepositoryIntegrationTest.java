package com.psc.kitchenInv.repository;

import com.psc.kitchenInv.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.psc.kitchenInv.Vegetable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VegRepositoryIntegrationTest {

    private VegetableRepository underTest;

    @Autowired
    public VegRepositoryIntegrationTest(VegetableRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatVegetableCanBeCreatedAndRecalled() {
        Vegetable v = TestDataUtil.buildVegA();

        underTest.save(v);
        Optional<Vegetable> result = underTest.findById(v.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(v);
    }

    @Test
    public void testThatManyVegetablesCanBeCreatedAndRecalled() {
        Vegetable v1 = TestDataUtil.buildVegA();
        underTest.save(v1);

        Vegetable v2 = TestDataUtil.buildVegB();
        underTest.save(v2);

        Vegetable v3 = TestDataUtil.buildVegC();
        underTest.save(v3);


        Iterable<Vegetable> results = underTest.findAll();

        assertThat(results).hasSize(3);
        assertThat(results).containsExactly(v1, v2, v3);

    }

    @Test
    public void testThatVegetableCanBeUpdated() {
        Vegetable v = TestDataUtil.buildVegA();
        underTest.save(v);
        v.setName("updated");
        underTest.save(v);
        Optional<Vegetable> result = underTest.findById(v.getId());
        assertThat(result.get()).isEqualTo(v);
    }

    @Test
    public void testThatVegetableCanBeDeleted() {
        Vegetable v = TestDataUtil.buildVegA();
        underTest.save(v);

        underTest.delete(v);
        Optional<Vegetable> result = underTest.findById(v.getId());
        assertThat(result).isEmpty();
    }
}
