package com.psc3245.KitchenInv_Polymorphism;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FoodService {

    private FoodRepository foodRepository;

    @Autowired
    private FoodService(FoodRepository foodRepository) { this.foodRepository = foodRepository; }

    public Food save(Food f) { return foodRepository.save(f); }

    public List<Food> findALl() {
        return StreamSupport.stream(
                foodRepository.findAll()
                        .spliterator(), false
        ).collect(Collectors.toList());
    }

    public Optional<Food> findOne(Long id) { return foodRepository.findById(id); }

    public boolean isExists(Long id) { return foodRepository.existsById(id); }

    public Food partialUpdate(Long id, Food f) {
        f.setId(id);

        return foodRepository.findById(id).map(existingFood -> {
            Optional.ofNullable(f.getName()).ifPresent(existingFood::setName);
            Optional.ofNullable(f.getCategory()).ifPresent(existingFood::setCategory);
            Optional.ofNullable(f.getSub_category()).ifPresent(existingFood::setSub_category);
            return foodRepository.save(existingFood);
        }).orElseThrow(() -> new RuntimeException("Food does not exist"));
    }

    public void delete(Long id) {
        foodRepository.deleteById(id);
    }
}
