package com.psc3245.KitchenInv_Polymorphism;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name="food")
public class Food {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Fruit, Vegetable, Meat,
    @Column(name = "category")
    private String category;

    // Further specifying upon category
    @Column(name = "name")
    private String name;

    // Will differ category to category - planning to expand upon this with polymorphism
    @Column(name = "sub_category")
    private String sub_category;

}
