package com.psc.kitchenInv;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.MeatClass;
import util.MeatType;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name="meat")
public class Meat {


    @Id
    @Column(name="id")
    Long id;

    @Column(name="name")
    private String name;

    @Column(name="meatClass")
    private MeatClass meatClass;

    @Column(name="meatType")
    private MeatType meatType;

    @Column(name="age")
    private int age;
}
