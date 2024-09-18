package com.psc.kitchenInv.services;

import com.psc.kitchenInv.Fruit;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface FruitService {

    Fruit save(Fruit f);

    List<Fruit> findAll();

    Page<Fruit> findAll(Pageable pageable);

    Optional<Fruit> findOne(Long id);

    boolean isExists(Long id);

    Fruit partialUpdate(Long id, Fruit fruit);

    void delete(Long id);
}
