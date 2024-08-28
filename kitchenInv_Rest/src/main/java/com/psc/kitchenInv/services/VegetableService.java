package com.psc.kitchenInv.services;

import com.psc.kitchenInv.Vegetable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VegetableService {

    Vegetable save(Vegetable v);

    List<Vegetable> findAll();

    Page<Vegetable> findAll(Pageable pageable);

    Optional<Vegetable> findOne(Long id);

    boolean isExists(Long id);

    Vegetable partialUpdate(Long id, Vegetable vegetable);

    void delete(Long id);
}
