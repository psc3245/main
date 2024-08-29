package com.psc.kitchenInv.repository;

import com.psc.kitchenInv.Meat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeatRepository extends CrudRepository<Meat, Long>, PagingAndSortingRepository<Meat, Long> {
}
