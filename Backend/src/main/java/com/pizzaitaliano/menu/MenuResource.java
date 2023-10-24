package com.pizzaitaliano.menu;

import com.pizzaitaliano.menu.exception.PizzaNotFoundException;
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
        try {
            menuService.findPizzaById(id);
        } catch (PizzaNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        try {
            menuService.findPizzaById(pizza.getId());
        } catch (PizzaNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Pizza updatePizza = menuService.updatePizza(pizza);
        return new ResponseEntity<>(updatePizza, HttpStatus.OK);
        }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deletePizza(@PathVariable("id") Long id) {
        try {
            menuService.findPizzaById(id);
        } catch (PizzaNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        menuService.deletePizza(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
