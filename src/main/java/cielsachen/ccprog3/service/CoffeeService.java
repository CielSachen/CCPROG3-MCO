package cielsachen.ccprog3.service;

import java.util.List;

import cielsachen.ccprog3.model.coffee.Coffee;
import cielsachen.ccprog3.model.truck.Truck;

public class CoffeeService {
    private final List<Coffee> coffees;

    public CoffeeService(List<Coffee> coffees) {
        this.coffees = coffees;
    }

    public List<Coffee> getCoffees() {
        return this.coffees;
    }

    public List<Coffee> getCoffeesByTruck(Truck truck) {
        return this.coffees.stream().filter((coffee) -> coffee.truckId == truck.id).toList();
    }

    public void addCoffee(Coffee coffee) {
        this.coffees.add(coffee);
    }
}
