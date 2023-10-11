import { Component, OnInit } from '@angular/core';
import { Pizza } from './pizza';
import { MenuService } from './menu.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Pizza-Italiano menu'
  public pizzas: Pizza[];
  public editPizza: Pizza;
  public deletePizza: Pizza;

  constructor(private menuService: MenuService) {}

ngOnInit() {
  this.getPizzas();
  this.pinappleAlarm(this.editPizza);
}

public getPizzas(): void {
    this.menuService.getPizzas().subscribe(
    (response: Pizza[]) => {
    this.pizzas = response;
  },
    (error: HttpErrorResponse) => {
      alert(error.message);
    }
   );
 }

public onAddPizza(addForm: NgForm): void {
  document.getElementById('add-pizza-form').click();
  this.menuService.addPizza(addForm.value).subscribe(
  (response: Pizza) => {
    console.log(response);
    this.getPizzas();
    addForm.reset();
    this.pinappleAlarm(response);
  },
  (error: HttpErrorResponse) => {
    alert(error.message);
    addForm.reset();
  }
  );
}

public onUpdatePizza(pizza: Pizza): void {
  this.menuService.updatePizza(pizza).subscribe(
  (response: Pizza) => {
    console.log(response);
    this.pinappleAlarm(pizza);
    this.getPizzas();
  },
  (error: HttpErrorResponse) => {
    alert(error.message);
  }
  );
}

public onDeletePizza(pizzaId: number): void {
  this.menuService.deletePizza(pizzaId).subscribe(
  (response: void) => {
    console.log(response);
    this.getPizzas();
  },
  (error: HttpErrorResponse) => {
    alert(error.message);
  }
  );
}

public searchePizzas(key: string): void {
  const results: Pizza[] = [];
  for (const pizza of this.pizzas) {
    if (pizza.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
    || pizza.ingredients.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
      results.push(pizza);
    }
  }
  this.pizzas = results;
  if (!key) {
    this.getPizzas();
  }
}

public pinappleAlarm(hawaiiPizza: Pizza): void {
  this.editPizza = hawaiiPizza;
  let substr= 'pineapple';
  if (hawaiiPizza.ingredients.includes(substr)){
    console.log('PINEAPPLE ON PIZZA');
    this.onOpenModal(hawaiiPizza, 'pineapple');
  }
}

 public onOpenModal(pizza: Pizza, mode: string): void {
  const container = document.getElementById('main-container');

  const button = document.createElement('button');
  button.type = 'button';
  button.style.display = 'none';
  button.setAttribute('data-toggle', 'modal');
  if (mode === 'add') {
    button.setAttribute('data-target', '#addPizzaModal');
  }
  if (mode === 'edit') {
    this.editPizza = pizza;
    button.setAttribute('data-target', '#updatePizzaModal');
  }
  if (mode === 'delete') {
    this.deletePizza = pizza;
    button.setAttribute('data-target', '#deletePizzaModal');
  }
  if (mode === 'pineapple') {
    this.deletePizza = pizza;
    button.setAttribute('data-target', '#pineapplePizzaModal');
  }
  container.appendChild(button);
  button.click();
 }
}
