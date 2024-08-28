package com.psc.kitchenInv.services.impl;


import com.psc.kitchenInv.Fruit;
import com.psc.kitchenInv.repository.FruitRepository;
import com.psc.kitchenInv.services.FruitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FruitServiceImpl implements FruitService {

    private FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Fruit save(Fruit f) {
        return fruitRepository.save(f);
    }

    @Override
    public List<Fruit> findAll() {
        return StreamSupport.stream(
                fruitRepository
                        .findAll()
                        .spliterator(), false
                )
                .collect(Collectors.toList());
    }

    @Override
    public Page<Fruit> findAll(Pageable pageable) {
        return fruitRepository.findAll(pageable);
    }

    @Override
    public Optional<Fruit> findOne(Long id) {
        return fruitRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return fruitRepository.existsById(id);
    }

    @Override
    public Fruit partialUpdate(Long id, Fruit fruit) {
        fruit.setId(id);

        return fruitRepository.findById(id).map(existingFruit -> {
            Optional.ofNullable(fruit.getName()).ifPresent(existingFruit::setName);
            Optional.ofNullable(fruit.getFruitClass()).ifPresent(existingFruit::setFruitClass);
            Optional.ofNullable(fruit.getAge()).ifPresent(existingFruit::setAge);
            return fruitRepository.save(existingFruit);
        }).orElseThrow(() -> new RuntimeException("Fruit does not exist."));
    }

    @Override
    public void delete(Long id) {
        fruitRepository.deleteById(id);
    }
}
