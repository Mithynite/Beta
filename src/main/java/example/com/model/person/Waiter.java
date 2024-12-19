package example.com.model.person;

import example.com.model.order.Order;
import example.com.model.order.OrderManager;
import example.com.util.EmployeeRole;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Waiter extends Employee {

    private int maxNumberOfActiveOrders;
    private static final int orderWaitTimeInMs = 100;

    private ArrayList<Order> activeOrders;
    private final ArrayList<Order> deliveredOrders;

    public Waiter(String name, String surname, int salary, OrderManager orderManager, long orderPreparationTime, int maxNumberOfActiveOrders) {
        super(name, surname, salary, orderManager, orderPreparationTime);
        this.maxNumberOfActiveOrders = maxNumberOfActiveOrders;
        this.activeOrders = new ArrayList<>();
        this.deliveredOrders = new ArrayList<>();
        role = EmployeeRole.Waiter;
    }

    //Method for avoiding duplicates in orders
    private boolean isDuplicateOrder(Order order) {
        return activeOrders.contains(order) || deliveredOrders.contains(order);
    }

    // Method for handling order delivery
    public String deliverOrder(Order order) throws InterruptedException {
        sleep();
        deliveredOrders.add(order);
        return "Order #" + order.getId() + " delivered. Here is your order, enjoy!";
    }

    @Override
    public String processOrders() throws InterruptedException {
        // Attempt to take a new order if under capacity
        if (activeOrders.size() < maxNumberOfActiveOrders) {
            Order newOrder = orderManager.getWaitersDeque().poll(orderWaitTimeInMs, TimeUnit.MILLISECONDS);

            if (newOrder != null){
                if(checkForPoisonPill(newOrder)){
                    isRunning.set(false);
                    return "Waiter " + name + " POISON PILL consumed!";
                }
                if(!isDuplicateOrder(newOrder)) {
                    newOrder.setEmployeeInChargeOfTheOrder(this);
                    activeOrders.add(newOrder);
                    // Forward the order for processing
                    if (newOrder.containsFood()) {
                        orderManager.forwardToChefQueue(newOrder);
                    }
                    if (newOrder.containsDrink()) {
                        orderManager.forwardToBartenderQueue(newOrder);
                    }

                    return name + " received and forwarded a new order: " + newOrder;
                }
            }
        }

        // Check all active orders for readiness using a for-loop
        for (int i = 0; i < activeOrders.size(); i++) {
            Order order = activeOrders.get(i);
            if (order.isReady()) {
                deliveredOrders.add(order);
                activeOrders.remove(i);
                i--;
                return name + " delivered order: " + order;
            }
        }
        Thread.sleep(500);
        return "";
    }
}