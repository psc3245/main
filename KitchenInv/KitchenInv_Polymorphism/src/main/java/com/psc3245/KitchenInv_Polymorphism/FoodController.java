package com.psc3245.KitchenInv_Polymorphism;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/food")
@CrossOrigin
public class FoodController {

    private FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) { this.foodService = foodService; }


    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody Food f) {
        Food saved = foodService.save(f);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Food> getFood() {
        List<Food> results = foodService.findALl();
        return results.stream()
                .collect(Collectors.toList());
    }

    @PutMapping
    public ResponseEntity<Food> putFood(@RequestBody Food f, @PathVariable Long id) {
        if (foodService.isExists(id)) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        f.setId(id);
        Food saved = foodService.save(f);

        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteFood(@PathVariable Long id) {
        if (foodService.isExists(id)) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        foodService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
