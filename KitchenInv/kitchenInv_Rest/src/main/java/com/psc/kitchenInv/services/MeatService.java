package com.psc.kitchenInv.services;

import com.psc.kitchenInv.Meat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MeatService {

    Meat save(Meat m);

    List<Meat> findAll();

    Page<Meat> findAll(Pageable pageable);

    Optional<Meat> findOne(Long id);

    boolean isExists(Long id);

    Meat partialUpdate(Long id, Meat meat);

    void delete(Long id);
}
