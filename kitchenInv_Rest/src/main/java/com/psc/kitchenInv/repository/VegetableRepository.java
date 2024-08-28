package com.psc.kitchenInv.repository;

import com.psc.kitchenInv.Fruit;
import com.psc.kitchenInv.Vegetable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VegetableRepository extends CrudRepository<Vegetable, Long>, PagingAndSortingRepository<Vegetable, Long> {
}
