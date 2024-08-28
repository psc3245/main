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
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vegetable_id_seq")
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="vegClass")
    private VegetableClass vegClass;

    @Column(name="age")
    private int age;

}
