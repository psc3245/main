package com.psc.kitchenInv;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.VegetableClass;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name="vegetables")
public class Vegetable {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="vegClass")
    private VegetableClass vegClass;

    @Column(name="age")
    private int age;

}
