import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Waiter extends Employee {

    private int maxNumberOfActiveOrders;
    private static final int orderWaitTimeInMs = 100;

    private ArrayList<Order> activeOrders;
    private final ArrayList<Order> deliveredOrders;

    public Waiter(String name, String surname, int salary, OrderManager orderManager, int maxNumberOfActiveOrders) {
        super(name, surname, salary, orderManager);
        this.maxNumberOfActiveOrders = maxNumberOfActiveOrders;
        this.activeOrders = new ArrayList<>();
        this.deliveredOrders = new ArrayList<>();
    }

    //Method for avoiding duplicates in orders
    private boolean isDuplicateOrder(Order order) {
        return activeOrders.contains(order) || deliveredOrders.contains(order);
    }

    // Method for handling order delivery
    public String deliverOrder(Order order) throws InterruptedException {
        sleep(3000);
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
                    newOrder.setEmployeeInChargeOfOrdering(this);
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
        sleep(500);
        return "";
    }
}