package com.pizzaitaliano.menu;


import com.pizzaitaliano.menu.model.Pizza;
import com.pizzaitaliano.menu.repo.PizzaRepo;
import com.pizzaitaliano.menu.service.MenuService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuResourceTest {

@Autowired
private PizzaRepo pizzaRepo;
private MenuService menuService;
private Pizza examplePizza;

@Test
@Order(1)
@Rollback(value = false)
    void savePizzaTest() {
    //given
    Pizza examplePizza = Pizza.builder().name("margaritta").ingredients("tomatoe")
            .price(21.37).vegan(true).pictureURL("image.jpg").build();

    pizzaRepo.save(examplePizza);

    Assertions.assertEquals("margaritta", examplePizza.getName());
}

@Test
@Order(2)
void getPizzaTest() {

    Pizza pizza = pizzaRepo.findPizzaById(11L).get();

    Assertions.assertEquals(21.37, pizza.getPrice());
}

@Test
@Order(3)
public void getAllPizzasTest() {

    List<Pizza> pizzas = pizzaRepo.findAll();

    Assertions.assertNotEquals(pizzas.size(), 0);

}



}

