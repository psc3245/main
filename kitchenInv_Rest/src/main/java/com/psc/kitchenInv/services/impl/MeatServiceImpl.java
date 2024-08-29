package com.psc.kitchenInv.services.impl;

import com.psc.kitchenInv.Meat;
import com.psc.kitchenInv.repository.MeatRepository;
import com.psc.kitchenInv.services.MeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MeatServiceImpl implements MeatService {

    @Autowired
    private MeatRepository meatRepository;

    public MeatServiceImpl(MeatRepository meatRepository) { this.meatRepository = meatRepository; }

    @Override
    public Meat save(Meat m) {
        return meatRepository.save(m);
    }

    @Override
    public List<Meat> findAll() {
        return  StreamSupport.stream(
                        meatRepository
                                .findAll()
                                .spliterator(), false
                )
                .collect(Collectors.toList());
    }

    @Override
    public Page<Meat> findAll(Pageable pageable) {
        return meatRepository.findAll(pageable);
    }

    @Override
    public Optional<Meat> findOne(Long id) {
        return meatRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return meatRepository.existsById(id);
    }

    @Override
    public Meat partialUpdate(Long id, Meat meat) {
        meat.setId(id);

        return meatRepository.findById(id).map(existingMeat -> {
            Optional.ofNullable(meat.getName()).ifPresent(existingMeat::setName);
            Optional.ofNullable(meat.getMeatClass()).ifPresent(existingMeat::setMeatClass);
            Optional.ofNullable(meat.getMeatType()).ifPresent(existingMeat::setMeatType);
            Optional.ofNullable(meat.getAge()).ifPresent(existingMeat::setAge);
            return meatRepository.save(existingMeat);
        }).orElseThrow(() -> new RuntimeException("Meat does not exist."));
    }

    @Override
    public void delete(Long id) {
        meatRepository.deleteById(id);
    }
}
