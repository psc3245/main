package com.psc.kitchenInv.controllers;


import com.psc.kitchenInv.Meat;
import com.psc.kitchenInv.dto.MeatDTO;
import com.psc.kitchenInv.mappers.impl.MeatMapperImpl;
import com.psc.kitchenInv.repository.MeatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeatController {

    private MeatRepository meatRepository;

    private MeatMapperImpl meatMapper;

    public MeatController(MeatRepository meatRepository, MeatMapperImpl meatMapper) {
        this.meatMapper = meatMapper;
        this.meatRepository = meatRepository;
    }

//    @PostMapping(path = "/meat")
//    public ResponseEntity<MeatDTO> createMeat(MeatDTO m) {
//        Meat meat = meatMapper.mapFrom(m);
//        Meat saved = meatRepository.save(meat);
//        return new ResponseEntity<>(meatMapper.mapTo(saved), HttpStatus.CREATED);
//    }
}
