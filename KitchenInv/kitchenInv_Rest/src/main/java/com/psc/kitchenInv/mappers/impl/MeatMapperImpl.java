package com.psc.kitchenInv.mappers.impl;

import com.psc.kitchenInv.Meat;
import com.psc.kitchenInv.dto.MeatDTO;
import com.psc.kitchenInv.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MeatMapperImpl implements Mapper<Meat, MeatDTO> {

    private ModelMapper modelMapper;

    public MeatMapperImpl(ModelMapper modelMapper) { this.modelMapper = modelMapper; }

    @Override
    public MeatDTO mapTo(Meat meat) {
        return modelMapper.map(meat, MeatDTO.class);
    }

    @Override
    public Meat mapFrom(MeatDTO meatDTO) {
        return modelMapper.map(meatDTO, Meat.class);
    }
}
