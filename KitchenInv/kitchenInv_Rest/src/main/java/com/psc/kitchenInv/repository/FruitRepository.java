package com.psc.kitchenInv.repository;

import com.psc.kitchenInv.Fruit;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import util.FruitClass;

import java.awt.print.Pageable;

@Repository
public interface FruitRepository extends CrudRepository<Fruit, Long>, PagingAndSortingRepository<Fruit, Long> {
}
