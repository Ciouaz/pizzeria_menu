package com.pizzaitaliano.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzaitaliano.menu.exception.PizzaNotFoundException;
import com.pizzaitaliano.menu.model.Pizza;
import com.pizzaitaliano.menu.service.MenuService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
class MenuResourceTest {

    @Autowired
    private MockMvc mockMv;
    @MockBean
    private MenuService menuService;

    @Autowired
    private ObjectMapper objectMapper;

    //JUnit test for POST pizza
    @Test
    void givenPizzaObject_whenAddPizza_thenReturnSavedPizza() throws Exception {

        //given
        Pizza pizza = Pizza.builder()
                .name("Margherita")
                .ingredients("mozzarella, tomatoes")
                .price(21.37)
                .vegan(true)
                .pictureURL("https://mondopazzo.pl/wp-content/uploads/2021/06/margherita.png")
                .build();
        given(menuService.addPizza(any(Pizza.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMv.perform(post("/pizza-italiano/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pizza)));
        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(pizza.getName())))
                .andExpect(jsonPath("$.ingredients", is(pizza.getIngredients())))
                .andExpect(jsonPath("$.price", is(pizza.getPrice())))
                .andExpect(jsonPath("$.vegan", is(pizza.isVegan())))
                .andExpect(jsonPath("$.pictureURL", is(pizza.getPictureURL())));
    }

    //JUnit test for GET all pizzas
    @Test
    void givenListOfPizzas_whenFindAllPizzas_thenReturnPizzasList() throws Exception {

        //given
        List<Pizza> listOfPizzas = new ArrayList<>();
        listOfPizzas.add(Pizza.builder()
                .name("Margherita")
                .ingredients("mozzarella, tomatoes")
                .price(21.37)
                .vegan(true)
                .pictureURL("https://mondopazzo.pl/wp-content/uploads/2021/06/margherita.png")
                .build());
        listOfPizzas.add(Pizza.builder()
                .name("Capriciossa")
                .ingredients("mozzarella, tomatoes, ham, mushrooms")
                .price(22.80)
                .vegan(false)
                .pictureURL("https://www.veropizza.com/storage/app/public/pizzas/May2020/lrYatE9HyMWFQOFXHiqY.gif")
                .build());
        given(menuService.findAllPizzas()).willReturn(listOfPizzas);
        //when
        ResultActions response = mockMv.perform(get("/pizza-italiano/menu"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfPizzas.size())));
    }

    //Positive scenario - valid pizza id
    //JUnit test for GET pizza by id
    @Test
    void givenPizzaId_whenFindPizzaById_thenReturnPizzaObject() throws Exception {

        //given
        long pizzaId = 1L;
        Pizza pizza = Pizza.builder()
                .name("Margherita")
                .ingredients("mozzarella, tomatoes")
                .price(21.37)
                .vegan(true)
                .pictureURL("https://mondopazzo.pl/wp-content/uploads/2021/06/margherita.png")
                .build();
        given(menuService.findPizzaById(pizzaId)).willReturn(pizza);

        //when
        ResultActions response = mockMv.perform(get("/pizza-italiano/menu/{id}", pizzaId));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(pizza.getName())))
                .andExpect(jsonPath("$.ingredients", is(pizza.getIngredients())))
                .andExpect(jsonPath("$.price", is(pizza.getPrice())))
                .andExpect(jsonPath("$.vegan", is(pizza.isVegan())))
                .andExpect(jsonPath("$.pictureURL", is(pizza.getPictureURL())));
    }

    //Negative scenario - valid pizza id
    //JUnit test for GET pizza by id
    @Test
    void givenPizzaId_whenFindPizzaById_thenReturnEmpty() throws Exception {

        //given
        long pizzaId = 1L;
        Pizza pizza = Pizza.builder()
                .name("Margherita")
                .ingredients("mozzarella, tomatoes")
                .price(21.37)
                .vegan(true)
                .pictureURL("https://mondopazzo.pl/wp-content/uploads/2021/06/margherita.png")
                .build();
        given(menuService.findPizzaById(pizzaId)).willThrow(new PizzaNotFoundException("Pizza by id " + pizzaId + " was not found"));

        //when
        ResultActions response = mockMv.perform(get("/pizza-italiano/menu/{id}", pizzaId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Positive scenario
    //JUnit test for UPDATE pizza
    @Test
    void givenUpdatePizza_whenUpdatePizza_thenReturnUpdatePizzaObject() throws Exception {

        //given
        long pizzaId = 1L;
                Pizza savedPizza = Pizza.builder()
                .name("Margherita")
                .ingredients("mozzarella, tomatoes")
                .price(21.37)
                .vegan(true)
                .pictureURL("https://mondopazzo.pl/wp-content/uploads/2021/06/margherita.png")
                .build();
        Pizza updatedPizza = Pizza.builder()
                .name("Capriciossa")
                .ingredients("mozzarella, tomatoes, ham, mushrooms")
                .price(22.80)
                .vegan(false)
                .pictureURL("https://www.veropizza.com/storage/app/public/pizzas/May2020/lrYatE9HyMWFQOFXHiqY.gif")
                .build();
        given(menuService.findPizzaById(pizzaId)).willReturn(savedPizza);
        given(menuService.updatePizza(any(Pizza.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        //when
        ResultActions response = mockMv.perform(put("/pizza-italiano/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPizza)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedPizza.getName())))
                .andExpect(jsonPath("$.ingredients", is(updatedPizza.getIngredients())))
                .andExpect(jsonPath("$.price", is(updatedPizza.getPrice())))
                .andExpect(jsonPath("$.vegan", is(updatedPizza.isVegan())))
                .andExpect(jsonPath("$.pictureURL", is(updatedPizza.getPictureURL())));
    }

    //Negative scenario
    //JUnit test for UPDATE pizza
    @Test
    void givenUpdatePizza_whenUpdatePizza_thenReturn404() throws Exception {

        //given
        long pizzaId = 1L;
        Pizza savedPizza = Pizza.builder()
                .id(pizzaId)
                .name("Margherita")
                .ingredients("mozzarella, tomatoes")
                .price(21.37)
                .vegan(true)
                .pictureURL("https://mondopazzo.pl/wp-content/uploads/2021/06/margherita.png")
                .build();
        Pizza updatedPizza = Pizza.builder()
                .id(pizzaId)
                .name("Capriciossa")
                .ingredients("mozzarella, tomatoes, ham, mushrooms")
                .price(22.80)
                .vegan(false)
                .pictureURL("https://www.veropizza.com/storage/app/public/pizzas/May2020/lrYatE9HyMWFQOFXHiqY.gif")
                .build();

        given(menuService.updatePizza(any(Pizza.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        given(menuService.findPizzaById(pizzaId)).willThrow(new PizzaNotFoundException("Pizza by id " + pizzaId + " was not found"));

        //when
        ResultActions response = mockMv.perform(put("/pizza-italiano/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPizza)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Positive scenario
    //Junit test for delete pizza
    @Test
    void givenPizzaId_whenDeletePizza_thenReturn200() throws Exception {

        //given
        long pizzaId = 1L;
        willDoNothing().given(menuService).deletePizza(pizzaId);

        //when
        ResultActions response = mockMv.perform(delete("/pizza-italiano/delete/{id}", pizzaId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }

    //Negative scenario
    //Junit test for delete pizza
    @Test
    void givenPizzaId_whenDeletePizza_thenReturn404() throws Exception {

        //given
        Long pizzaId = null;
        willThrow(new PizzaNotFoundException("Pizza by id " + pizzaId + " was not found"))
                .given(menuService).deletePizza(pizzaId);

        //when
        ResultActions response = mockMv.perform(delete("/pizza-italiano/delete/{id}", pizzaId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

}

