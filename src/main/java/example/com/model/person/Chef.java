package example.com.model.person;

import example.com.model.order.Order;
import example.com.model.order.OrderManager;
import example.com.util.EmployeeRole;

public class Chef extends Employee{

    public Chef(String name, String surname, int salary, OrderManager orderManager, long orderPreparationTime) {
        super(name, surname, salary, orderManager, orderPreparationTime);
        role = EmployeeRole.Chef;
    }

    @Override
    public String processOrders() throws InterruptedException {
        Order newOrder = orderManager.getChefsDeque().take();
        if(checkForPoisonPill(newOrder)){
            isRunning.set(false);
            return "Chef " + name + ": POISON PILL consumed!";
        }
        System.out.println("Chef " + name + " is preparing food for Order #" + newOrder.getId());

        // Simulate food preparation
        sleep();
        newOrder.setFoodReady();

        orderManager.forwardToWaiterQueue(newOrder);
        return "Chef " + name + " finished preparing food for Order #" + newOrder.getId();
    }
}
