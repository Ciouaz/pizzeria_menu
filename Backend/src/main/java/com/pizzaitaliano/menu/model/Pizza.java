package com.pizzaitaliano.menu.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Pizza implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String ingredients;
    private double price;
    private boolean vegan;
    private String pictureURL;

}
