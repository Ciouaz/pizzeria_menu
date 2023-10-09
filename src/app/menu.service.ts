import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pizza } from './pizza';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})

export class MenuService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public getPizzas(): Observable<Pizza[]> {
        return this.http.get<Pizza[]>(`${this.apiServerUrl}/pizza-italiano/menu`);
    }

    public addPizza(pizza: Pizza): Observable<Pizza> {
        return this.http.post<Pizza>(`${this.apiServerUrl}/pizza-italiano/add`, pizza);
    }

    public updatePizza(pizza: Pizza): Observable<Pizza> {
        return this.http.put<Pizza>(`${this.apiServerUrl}/pizza-italiano/update`, pizza);
    }

    public deletePizza(pizzaId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/pizza-italiano/delete/${pizzaId}`);
    }

}