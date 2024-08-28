package com.psc.kitchenInv;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.FruitClass;
import util.MeatClass;
import util.MeatType;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name="Meat")
public class Meat {

    @Id
    int id;

    @Column(name="name")
    private String name;

    @Column(name="meatClass")
    private MeatClass meatClass;

    @Column(name="meatType")
    private MeatType meatType;

    @Column(name="age")
    private int age;
}
