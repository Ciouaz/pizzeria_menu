package com.pizzaitaliano.menu;

import com.pizzaitaliano.menu.model.Pizza;
import com.pizzaitaliano.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizza-italiano")
public class MenuResource {

    private final MenuService menuService;

    public MenuResource(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menu")
    public ResponseEntity<List<Pizza>> getAllPizzas() {
        List<Pizza> pizzas = menuService.findAllPizzas();
        return new ResponseEntity<>(pizzas, HttpStatus.OK);
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable("id") Long id) {
        Pizza pizza = menuService.findPizzaById(id);
        return new ResponseEntity<>(pizza, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Pizza> addPizza(@RequestBody Pizza pizza) {
        Pizza newPizza = menuService.addPizza(pizza);
        return new ResponseEntity<>(newPizza, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Pizza> updatePizza(@RequestBody Pizza pizza) {
        Pizza updatePizza = menuService.updatePizza(pizza);
        if (pizza.getId() != null) {
            return new ResponseEntity<>(updatePizza, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(updatePizza, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePizza(@PathVariable("id") Long id) {
        Pizza pizza = menuService.findPizzaById(id);
        menuService.deletePizza(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
