package com.psc.kitchenInv.services.impl;

import com.psc.kitchenInv.Vegetable;
import com.psc.kitchenInv.repository.VegetableRepository;
import com.psc.kitchenInv.services.VegetableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VegetableServiceImpl implements VegetableService {

    private VegetableRepository vegetableRepository;

    public VegetableServiceImpl(VegetableRepository vegetableRepository) {
        this.vegetableRepository = vegetableRepository;
    }

    @Override
    public Vegetable save(Vegetable v) {
        return vegetableRepository.save(v);
    }

    @Override
    public List<Vegetable> findAll() {
        return StreamSupport.stream(
                        vegetableRepository
                                .findAll()
                                .spliterator(), false
                )
                .collect(Collectors.toList());
    }

    @Override
    public Page<Vegetable> findAll(Pageable pageable) {
        return vegetableRepository.findAll(pageable);
    }

    @Override
    public Optional<Vegetable> findOne(Long id) {
        return vegetableRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return vegetableRepository.existsById(id);
    }

    @Override
    public Vegetable partialUpdate(Long id, Vegetable vegetable) {
        vegetable.setId(id);

        return vegetableRepository.findById(id).map(existingVegetable -> {
            Optional.ofNullable(vegetable.getName()).ifPresent(existingVegetable::setName);
            Optional.ofNullable(vegetable.getVegClass()).ifPresent(existingVegetable::setVegClass);
            Optional.ofNullable(vegetable.getAge()).ifPresent(existingVegetable::setAge);
            return vegetableRepository.save(existingVegetable);
        }).orElseThrow(() -> new RuntimeException("Vegetable does not exist."));
    }

    @Override
    public void delete(Long id) {
        vegetableRepository.deleteById(id);
    }
}
