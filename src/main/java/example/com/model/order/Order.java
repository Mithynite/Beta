package example.com.model.order;

import example.com.model.person.Customer;
import example.com.model.person.Employee;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    private int id;
    private Customer customer;
    private Date orderDate;
    private String food;
    private String drink;
    private final AtomicBoolean foodReady = new AtomicBoolean(false);
    private final AtomicBoolean drinkReady = new AtomicBoolean(false);
    private Employee employeeInChargeOfTheOrder;

    public Order(Customer customer, String food, String drink) {
        this.id = idGenerator.getAndIncrement();
        this.customer = customer;
        this.orderDate = new Date();
        this.food = food;
        this.drink = drink;
    }

    public int getId() {
        return id;
    }

    public void setFoodReady() {
        foodReady.set(true);
    }

    public void setDrinkReady() {
        drinkReady.set(true);
    }

    public void setEmployeeInChargeOfTheOrder(Employee employeeInChargeOfTheOrder) {
        this.employeeInChargeOfTheOrder = employeeInChargeOfTheOrder;
    }

    public String getFood() {
        return food;
    }

    public String getDrink() {
        return drink;
    }

    public boolean containsFood(){
        return !this.food.isEmpty();
    }

    public boolean containsDrink(){
        return !this.drink.isEmpty();
    }

    public synchronized boolean isReady() {
        return (!containsFood() || foodReady.get()) && (!containsDrink() || drinkReady.get());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", food='" + food + '\'' +
                ", drink='" + drink + '\'' +
                "', READY = '" + isReady() +
                '}';
    }
}
