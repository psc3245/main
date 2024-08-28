package com.psc.kitchenInv.mappers.impl;

import com.psc.kitchenInv.Vegetable;
import com.psc.kitchenInv.dto.VegetableDTO;
import com.psc.kitchenInv.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VegetableMapperImpl implements Mapper<Vegetable, VegetableDTO> {

    private ModelMapper modelMapper;

    public VegetableMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public VegetableDTO mapTo(Vegetable vegetable) {
        return modelMapper.map(vegetable, VegetableDTO.class);
    }

    @Override
    public Vegetable mapFrom(VegetableDTO vegetableDTO) {
        return modelMapper.map(vegetableDTO, Vegetable.class);
    }
}
