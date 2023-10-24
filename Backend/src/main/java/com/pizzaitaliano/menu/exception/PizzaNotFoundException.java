package com.pizzaitaliano.menu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PizzaNotFoundException extends RuntimeException{

     public PizzaNotFoundException(String message) {
        super (message);
    }
}