package com.pizzaitaliano.menu.repo;

import com.pizzaitaliano.menu.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepo extends JpaRepository<Pizza, Long> {
    Optional <Pizza> findPizzaById(Long id);
}
