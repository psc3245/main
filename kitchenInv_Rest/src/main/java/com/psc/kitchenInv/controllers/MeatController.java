package com.psc.kitchenInv.controllers;


import com.psc.kitchenInv.Meat;
import com.psc.kitchenInv.dto.FruitDTO;
import com.psc.kitchenInv.dto.MeatDTO;
import com.psc.kitchenInv.mappers.impl.MeatMapperImpl;
import com.psc.kitchenInv.repository.MeatRepository;
import com.psc.kitchenInv.services.MeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MeatController {

    private MeatService meatService;

    private MeatMapperImpl meatMapper;

    public MeatController(MeatService meatService, MeatMapperImpl meatMapper) {
        this.meatMapper = meatMapper;
        this.meatService = meatService;
    }

    @PostMapping(path = "/meat")
    public ResponseEntity<MeatDTO> createMeat(@RequestBody MeatDTO m) {
        Meat meat = meatMapper.mapFrom(m);
        Meat saved = meatService.save(meat);
        return new ResponseEntity<>(meatMapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping(path = "/meat")
    public List<MeatDTO> listMeat() {
        List<Meat> results = meatService.findAll();
        return results.stream()
                .map(meatMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/meat/{id}")
    public ResponseEntity<MeatDTO> getByID(@PathVariable Long id) {
        Optional<Meat> result = meatService.findOne(id);
        return result.map(meat -> {
            MeatDTO meatDTO = meatMapper.mapTo(meat);
            return new ResponseEntity<>(meatDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/meat/{id}")
    public ResponseEntity<MeatDTO> putMeat(
            @PathVariable("id") Long id, @RequestBody MeatDTO meatDTO
    ) {
        if (!meatService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        meatDTO.setId(id);
        Meat meat = meatMapper.mapFrom(meatDTO);
        Meat saved = meatService.save(meat);
        return new ResponseEntity<>(meatMapper.mapTo(saved), HttpStatus.OK);
    }

    @PatchMapping(path = "/meat/{id}")
    public ResponseEntity<MeatDTO> patchMeat(
            @PathVariable("id") Long id, @RequestBody MeatDTO meatDTO
    ) {
        if (!meatService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        meatDTO.setId(id);
        Meat meat = meatMapper.mapFrom(meatDTO);
        Meat saved = meatService.partialUpdate(id, meat);
        return new ResponseEntity<>(meatMapper.mapTo(saved), HttpStatus.OK);
    }

    @DeleteMapping(path = "/meat/{id}")
    public ResponseEntity deleteMeat(@PathVariable("id") Long id) {
        if (!meatService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        meatService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
