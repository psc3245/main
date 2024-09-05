package com.psc.kitchenInv.controllers;

import com.psc.kitchenInv.Fruit;
import com.psc.kitchenInv.dto.FruitDTO;
import com.psc.kitchenInv.mappers.Mapper;
import com.psc.kitchenInv.services.FruitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class FruitController {

    private FruitService fruitService;

    private Mapper<Fruit, FruitDTO> fruitMapper;

    public FruitController(FruitService fruitService, Mapper<Fruit, FruitDTO> fruitMapper) {
        this.fruitService = fruitService;
        this.fruitMapper = fruitMapper;
    }

    @PostMapping(path= "/fruit")
    public ResponseEntity<FruitDTO> createFruit(@RequestBody FruitDTO f) {
        Fruit fruit = fruitMapper.mapFrom(f);
        Fruit saved = fruitService.save(fruit);
        return new ResponseEntity<>(fruitMapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping(path = "/fruit")
    public List<FruitDTO> listFruit() {
        List<Fruit> results = fruitService.findAll();
        return results.stream()
                .map(fruitMapper::mapTo)
                .collect(Collectors.toList());
    }

// Pagination: messes up a test
    // Also pagination is not that useful atm so I am going to leave it disabled
//    @GetMapping(path = "/fruit")
//    public Page<FruitDTO> listFruit(Pageable pageable) {
//        Page<Fruit> results = fruitService.findAll(pageable);
//        return results.map(fruitMapper::mapTo);
//    }

    @GetMapping(path = "/fruit/{id}")
    public ResponseEntity<FruitDTO> getFruit(@PathVariable("id") Long id) {
        Optional<Fruit> foundFruit = fruitService.findOne(id);
        return foundFruit.map(fruit -> {
            FruitDTO fruitDTO = fruitMapper.mapTo(fruit);
            return new ResponseEntity<>(fruitDTO, HttpStatus.OK);
        }).orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/fruit/{id}")
    public ResponseEntity<FruitDTO> fullUpdateFruit(
            @PathVariable("id") Long id, @RequestBody FruitDTO fruitDTO) {
        if (!fruitService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        fruitDTO.setId(id);
        Fruit fruit = fruitMapper.mapFrom(fruitDTO);
        Fruit saved = fruitService.save(fruit);
        return new ResponseEntity<>(fruitMapper.mapTo(saved), HttpStatus.OK);

    }

    @PatchMapping(path = "/fruit/{id}")
    public ResponseEntity<FruitDTO> partialUpdateFruit(
            @PathVariable("id") Long id, @RequestBody FruitDTO fruitDTO) {
        if (!fruitService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Fruit fruit = fruitMapper.mapFrom(fruitDTO);
        Fruit updated = fruitService.partialUpdate(id, fruit);
        return new ResponseEntity<>(fruitMapper.mapTo(updated), HttpStatus.OK);
    }

    @DeleteMapping(path = "/fruit/{id}")
    public ResponseEntity deleteFruit(@PathVariable("id") Long id) {
        fruitService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
