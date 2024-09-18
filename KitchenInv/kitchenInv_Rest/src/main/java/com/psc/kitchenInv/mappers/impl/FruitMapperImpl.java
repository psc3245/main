package com.psc.kitchenInv.mappers.impl;

import com.psc.kitchenInv.Fruit;
import com.psc.kitchenInv.dto.FruitDTO;
import com.psc.kitchenInv.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Component;

@Component
public class FruitMapperImpl implements Mapper<Fruit, FruitDTO> {

    private ModelMapper modelMapper;

    public FruitMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FruitDTO mapTo(Fruit fruit) {
        return modelMapper.map(fruit, FruitDTO.class);
    }

    @Override
    public Fruit mapFrom(FruitDTO fruitDTO) {
        return modelMapper.map(fruitDTO, Fruit.class);
    }
}
