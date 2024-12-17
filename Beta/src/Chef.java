import java.util.Date;

public class Chef extends Employee{

    public Chef(String name, String surname, int salary, OrderManager orderManager) {
        super(name, surname, salary, orderManager);
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
        sleep(5000);
        newOrder.setFoodReady();

        orderManager.forwardToWaiterQueue(newOrder);
        return "Chef " + name + " finished preparing food for Order #" + newOrder.getId();
    }
}
