package com.psc.kitchenInv.controllers;

import com.psc.kitchenInv.Vegetable;
import com.psc.kitchenInv.dto.VegetableDTO;
import com.psc.kitchenInv.mappers.Mapper;
import com.psc.kitchenInv.services.VegetableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class VegetableController {

    private VegetableService vegetableService;

    private Mapper<Vegetable, VegetableDTO> vegetableMapper;

    public VegetableController(
            VegetableService vegetableService, Mapper<Vegetable, VegetableDTO> vegetableMapper) {
        this.vegetableService = vegetableService;
        this.vegetableMapper = vegetableMapper;
    }

    @PostMapping(path = "/vegetables")
    public ResponseEntity<VegetableDTO> createVegetable(@RequestBody VegetableDTO v) {
        Vegetable veg = vegetableMapper.mapFrom(v);
        Vegetable saved = vegetableService.save(veg);
        return new ResponseEntity<>(vegetableMapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping(path = "/vegetables")
    public List<VegetableDTO> listVegetables() {
        List<Vegetable> results = vegetableService.findAll();
        return results.stream()
                .map(vegetableMapper::mapTo)
                .collect(Collectors.toList());
    }

// Pagination: messes up a test
    // Also pagination is not that useful atm so I am going to leave it disabled
//    @GetMapping(path = "/vegetables")
//    public Page<VegetableDTO> listVegetable(Pageable pageable) {
//        Page<Vegetable> results = vegetableService.findAll(pageable);
//        return results.map(vegetableMapper::mapTo);
//    }

    @GetMapping(path = "/vegetables/{id}")
    public ResponseEntity<VegetableDTO> getFruit(@PathVariable("id") Long id) {
        Optional<Vegetable> foundVeg = vegetableService.findOne(id);

        return foundVeg.map(vegetable -> {
            VegetableDTO vegetableDTO = vegetableMapper.mapTo(vegetable);
            return new ResponseEntity<>(vegetableDTO, HttpStatus.OK);
        }).orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/vegetables/{id}")
    public ResponseEntity<VegetableDTO> fullUpdateVegetable(
            @PathVariable("id") Long id, @RequestBody VegetableDTO vegetableDTO){
        if (!vegetableService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vegetableDTO.setId(id);
        Vegetable veg = vegetableMapper.mapFrom(vegetableDTO);
        Vegetable updated = vegetableService.partialUpdate(id, veg);
        return new ResponseEntity<>(vegetableMapper.mapTo(updated), HttpStatus.OK);
    }

    @PatchMapping(path = "/vegetables/{id}")
    public ResponseEntity<VegetableDTO> partialUpdateVegetable(
            @PathVariable("id") Long id, @RequestBody VegetableDTO vegetableDTO) {
        if (!vegetableService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Vegetable veg = vegetableMapper.mapFrom(vegetableDTO);
        Vegetable updated = vegetableService.partialUpdate(id, veg);
        return new ResponseEntity<>(vegetableMapper.mapTo(updated), HttpStatus.OK);
    }

    @DeleteMapping(path = "/vegetables/{id}")
    public ResponseEntity deleteVegetable(@PathVariable("id") Long id) {
        vegetableService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
