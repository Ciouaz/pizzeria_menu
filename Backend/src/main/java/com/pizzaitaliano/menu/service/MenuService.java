package com.pizzaitaliano.menu.service;

import com.pizzaitaliano.menu.exception.PizzaNotFoundException;
import com.pizzaitaliano.menu.model.Pizza;
import com.pizzaitaliano.menu.repo.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final PizzaRepo pizzaRepo;

    @Autowired
    public MenuService(PizzaRepo pizzaRepo) {
        this.pizzaRepo = pizzaRepo;
    }

    public Pizza addPizza(Pizza pizza) {
        return pizzaRepo.save(pizza);
    }

    public Pizza updatePizza(Pizza pizza) {
        return pizzaRepo.save(pizza);
    }

    public List<Pizza> findAllPizzas() {
        return pizzaRepo.findAll();
    }

    public Pizza findPizzaById(Long id) {
        return pizzaRepo.findPizzaById(id)
                .orElseThrow(()-> new PizzaNotFoundException("Pizza by id " + id + " was not found"));
    }

    public void deletePizza(Long id) {
        pizzaRepo.deleteById(id);
    }
}
