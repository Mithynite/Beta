package example.com.model.person;

import example.com.model.order.Order;
import example.com.model.order.OrderManager;
import example.com.util.EmployeeRole;

import java.util.concurrent.atomic.AtomicBoolean;

public class Employee extends Person implements Runnable {
    protected int salary;
    protected OrderManager orderManager;
    protected static final AtomicBoolean isRunning = new AtomicBoolean(true);
    protected EmployeeRole role;
    protected long orderPreparationTime;

    public Employee(String name, String surname, int salary, OrderManager orderManager, long orderPreparationTime) {
        super(name, surname);
        this.salary = validateSalary(salary);
        if(orderManager == null){
            throw new NullPointerException("OrderManager cannot be null!");
        }
        if(orderPreparationTime <= 0){
            throw new IllegalArgumentException("Order preparation time must be a positive integer!");
        }
        this.orderManager = orderManager;
        this.orderPreparationTime = orderPreparationTime;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public String processOrders() throws InterruptedException{
        return null;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() && isRunning.get()) {
                System.out.println(processOrders());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void sleep() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Thread was interrupted before sleep.");
        }
        Thread.sleep(orderPreparationTime);
    }

    public boolean checkForPoisonPill(Order order) {
        return orderManager.isPoisonPill(order);
    }

    private int validateSalary(int salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be a positive integer!");
        }
        return salary;
    }
}
