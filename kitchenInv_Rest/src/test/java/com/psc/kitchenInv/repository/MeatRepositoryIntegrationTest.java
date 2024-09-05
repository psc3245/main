package com.psc.kitchenInv.repository;

import com.psc.kitchenInv.Meat;
import com.psc.kitchenInv.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MeatRepositoryIntegrationTest {


    private MeatRepository underTest;

    @Autowired
    public MeatRepositoryIntegrationTest(MeatRepository meatRepository) { this.underTest = meatRepository; }

    @Test
    public void TestThatMeatCanBeCreatedAndRecalled() {
        Meat m = TestDataUtil.buildMeatA();
        underTest.save(m);
        Optional<Meat> result = underTest.findById(m.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(m);
    }

    @Test
    public void TestThatManyMeatCanBeCreatedAndRecalled() {
        Meat m1 = TestDataUtil.buildMeatA();
        underTest.save(m1);
        Meat m2 = TestDataUtil.buildMeatB();
        underTest.save(m2);
        Meat m3 = TestDataUtil.buildMeatC();
        underTest.save(m3);

        Iterable<Meat> results = underTest.findAll();

        assertThat(results).hasSize(3);
        assertThat(results).containsExactly(m1, m2, m3);
    }

    @Test
    public void TestThatMeatCanBeFullUpdated() {
        Meat m1 = TestDataUtil.buildMeatA();
        underTest.save(m1);
        m1.setName("updated");
        underTest.save(m1);

        Optional<Meat> result = underTest.findById(m1.getId());

        assertThat(result.get().getName()).isEqualTo("updated");
    }

    @Test
    public void TestThatMeatCanBeDeleted() {
        Meat m1 = TestDataUtil.buildMeatA();
        underTest.save(m1);

        underTest.deleteById(m1.getId());

        Optional<Meat> result = underTest.findById(m1.getId());

        assertThat(result).isEmpty();
    }


}
