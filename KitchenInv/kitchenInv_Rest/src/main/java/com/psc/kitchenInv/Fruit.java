package com.psc.kitchenInv;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.FruitClass;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name="fruit")
public class Fruit {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="fruitClass")
    private FruitClass fruitClass;

    @Column(name="age")
    private int age;

}
